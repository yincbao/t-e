package com.hoperun.oauth.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.service.IKrbPrincipleService;

@Controller
public class LoginController {

	@Autowired
	private IKrbPrincipleService userService;

	@RequestMapping(value = "/login")
	public String showLoginForm(HttpServletRequest req, Model model) {
		Subject subject = SecurityUtils.getSubject();

		if (subject.isAuthenticated()) {
			KrbUser user = userService.findValidRequireDefRealm((String) subject.getPrincipal());
			if(user!=null){
				model.addAttribute("user", user);
				return "/main/index";
			}
			
		}

		String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
		if ("null".equalsIgnoreCase(exceptionClassName) || "".equals(exceptionClassName))
			exceptionClassName = null;
		String error = null;
		if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
			error = "用户名/密码错误";
		} else if (exceptionClassName != null) {
			error = "其他错误：" + exceptionClassName;
		}
		model.addAttribute("error", error);
		return "login";
	}

	@RequestMapping(value = "/user/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			subject.logout();
		}
		return "redirect:/login";
	}

}
