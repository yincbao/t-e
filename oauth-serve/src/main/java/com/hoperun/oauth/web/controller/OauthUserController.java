package com.hoperun.oauth.web.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.utils.OAuthUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hoperun.oauth.entity.constants.AuthorizationStatus;
import com.hoperun.oauth.entity.po.AuthorizationApplications;
import com.hoperun.oauth.entity.po.AuthorizationRequierment;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.po.OauthClient;
import com.hoperun.oauth.service.IOAuthClientService;
import com.hoperun.oauth.service.IOAuthServerService;
import com.hoperun.oauth.service.IOAuthorizationApplicationsService;
import com.hoperun.oauth.service.IOAuthorizationRequiermentService;
import com.hoperun.oauth.utils.StringUtil;

/**
 * @ClassName OauthUserResource.java
 * @Description
 * @author yin_changbao
 * @param <AuthorizationApplications>
 * @Date May 25, 2016 6:12:10 PM
 *
 */
@Controller
public class OauthUserController {
	private static final Logger logger = LoggerFactory.getLogger(OauthUserController.class);

	private static final String IDP_REALM = "HOPERUN-KERBEROS.COM";

	@Autowired
	private IOAuthClientService oauthClientService;
	@Autowired
	private IOAuthorizationRequiermentService oauthorizationRequiermentService;
	@Autowired
	private IOAuthorizationApplicationsService oauthorizationApplicationsService;

	@Autowired
	private IOAuthServerService oAuthServerService;

	@RequestMapping("/authorize")
	public Object authorize(Model model, HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

		try {
			String qs = request.getQueryString();
			OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);
			OauthClient client = oauthClientService.findByClientId(oauthRequest.getClientId());
			model.addAttribute("client", client);
			if (client == null) {
				OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						.setError(OAuthError.TokenResponse.INVALID_CLIENT).setErrorDescription("客户端验证失败，如错误的client_id/client_secret。")
						.buildJSONMessage();
				return new ResponseEntity(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
			}
			String clientId = oauthRequest.getClientId();
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				if (!login(subject, request, client.getClientRealm().getRealm())) {
					model.addAttribute("client", client);
					return "oauth2login";
				}
			}
			KrbUser resourceOwner = oauthClientService.findOwnerById(clientId);
			String username = (String) subject.getPrincipal();
			String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);
			AuthorizationRequierment areq = client.getAr();
			if (areq != null && areq.getAuthorizationsToUser() != null && !areq.getAuthorizationsToUser().isEmpty()) {
				logger.debug("client {} needs user to approve for some permisions, list size is {}", clientId, areq.getAuthorizationsToUser().size());
				// check resource authorization requirements

				AuthorizationApplications aar = oauthorizationApplicationsService.findLatestWithRoles(username, resourceOwner.getPrinc(),
						areq.getId());
				if (aar == null || !aar.getAuthorizationStatus().equals(AuthorizationStatus.OK) || aar.isOverdate()) {// 尚未向用户申请过，或者用户尚未批准且尚未过期
					if (submitAuth(request))
						oauthorizationApplicationsService.approveAndNotify(username, resourceOwner.getPrinc(), clientId);
					else {
						model.addAttribute("clientARs", areq.getAuthorizationsToUser());
						return "confirmAuth";
					}
				}
			} else
				logger.debug("client {} don't ask user to approve any permisions", clientId);

			if (areq != null && areq.getAuthorizationsToResource() != null && !areq.getAuthorizationsToResource().isEmpty()) {
				logger.debug("client {} needs user to provide some permisions, list size is {}", clientId, areq.getAuthorizationsToResource().size());
				// check client authorization requirements
				AuthorizationApplications aac = oauthorizationApplicationsService.findLatestWithRoles(resourceOwner.getPrinc(), username,
						areq.getId());
				model.addAttribute("clientARs", areq.getAuthorizationsToResource());
				if (aac == null) {
					// establish req
					oauthorizationApplicationsService.saveAndNotify(resourceOwner.getPrinc(), username, clientId);
					return "waitForApprove";
				} else if (aac.getAuthorizationStatus() == AuthorizationStatus.PENDING && !aac.isOverdate()) {
					// authorizationApplicationsService.notify(clientId,username);
					return "waitForApprove";
				} else if (aac.getAuthorizationStatus() == AuthorizationStatus.DENY) {
					return "requestDenied";
				} else if (aac.getAuthorizationStatus() != AuthorizationStatus.DENY && aac.isOverdate()) {
					// re-auth
					return "oauth2login";
				}
			} else
				logger.debug("client {} don't ask user to provide any permisions", clientId);

			String authorizationCode = null;
			// responseType 选择official 推荐的code模式，更安全
			String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
			if (responseType.equals(ResponseType.CODE.toString())) {
				OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
				authorizationCode = oauthIssuerImpl.authorizationCode();
				oAuthServerService.addAuthCode(authorizationCode, username);
			}

			OAuthASResponse.OAuthAuthorizationResponseBuilder builder = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND);
			builder.setCode(authorizationCode);
			final OAuthResponse response = builder.location(redirectURI).buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		} catch (OAuthProblemException e) {
			String redirectUri = e.getRedirectUri();
			if (OAuthUtils.isEmpty(redirectUri))
				return new ResponseEntity("OAuth callback url needs to be provided by client!!!", HttpStatus.NOT_FOUND);
			final OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_FOUND).error(e).location(redirectUri)
					.buildQueryMessage();
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(new URI(response.getLocationUri()));
			return new ResponseEntity(headers, HttpStatus.valueOf(response.getResponseStatus()));
		}
	}

	/**
	 * @param request
	 * @return
	 */
	private boolean submitAuth(HttpServletRequest request) {
		String aus = fetchFromReq(request, "cfmRes");
		if (!StringUtil.isEmpty(aus) && "auth".equalsIgnoreCase(aus))
			return true;
		return false;
	}

	private String fetchFromReq(HttpServletRequest req, String paramName) {
		String value = req.getParameter(paramName);
		if (StringUtil.isEmpty(value))
			value = (String) req.getAttribute(paramName);
		return value;
	}

	private boolean login(Subject subject, HttpServletRequest request, String realm) {
		if ("get".equalsIgnoreCase(request.getMethod())) {
			return false;
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		try {
			subject.login(token);
			return true;
		} catch (Exception e) {
			request.setAttribute("error", "登录失败:" + e.getClass().getName());
			return false;
		}
	}

}
