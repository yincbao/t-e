package com.hoperun.oauth.web.rest;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoperun.oauth.entity.constants.AuthorizationStatus;
import com.hoperun.oauth.entity.po.AuthorizationApplications;
import com.hoperun.oauth.service.IKrbPrincipleService;
import com.hoperun.oauth.service.IKrbRealmService;
import com.hoperun.oauth.service.IOAuthClientService;
import com.hoperun.oauth.service.IOAuthorizationApplicationsService;

@Controller
@RequestMapping("/api/client")
public class ClientResource {

	@Autowired
	private IOAuthClientService clientService;
	
	@Autowired
	IKrbPrincipleService krbPrincipleService;
	@Autowired
	IKrbRealmService krbRealmService;
	@Autowired
	IOAuthorizationApplicationsService oauthorizationApplicationsService;

	@RequestMapping(value="/approveRequests",method = RequestMethod.GET)
	@ResponseBody
	public List<AuthorizationApplications> listAreqs() {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		return oauthorizationApplicationsService.findWaitForApproveByResourceOwner(username);
	}
	
	@RequestMapping(value="/{id}/deal",method = RequestMethod.GET)
	public String listAreqs(@PathVariable("id") Long id,Model model,@RequestParam(value="result") int result) {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		oauthorizationApplicationsService.updateStatusById(id,username,AuthorizationStatus.fromElem(result));
		return "redirect:/client/approveRequests";
	}

}
