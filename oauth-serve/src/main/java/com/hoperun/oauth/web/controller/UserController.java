package com.hoperun.oauth.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hoperun.oauth.entity.po.KrbRealm;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.po.OauthClient;
import com.hoperun.oauth.exception.UserNameDuplicateExceptioin;
import com.hoperun.oauth.service.IKrbPrincipleService;
import com.hoperun.oauth.service.IOAuthorizationApplicationsService;
import com.hoperun.oauth.utils.Page;
import com.hoperun.oauth.utils.PageUtil;
import com.hoperun.oauth.utils.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IKrbPrincipleService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(HttpServletRequest request, Model model) {
		Page<KrbUser> page = new Page<KrbUser>(PageUtil.PAGE_SIZE);
		PageUtil.init(page, request);
		model.addAttribute("page", userService.findAll(page));
		return "user/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute("user", new KrbUser());
		model.addAttribute("op", "create");
		return "user/edit";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(KrbUser user, Model model) {
		if (user == null || StringUtil.isEmpty(user.getPrinc()) || StringUtil.isEmpty(user.getPassword())) {
			model.addAttribute("msg", "新增失败，用户密码不能为空！");
			model.addAttribute("op", "create");
			model.addAttribute("user", new KrbUser());
			return "user/edit";
		}
		KrbUser usr = null;
		try {
			usr = userService.createUser(user,
					user.getRealms() == null || StringUtil.isEmpty(user.getDefaultRealm().getRealm()) ? "HOPERUN-KERBEROS.COM" : user
							.getDefaultRealm().getRealm());
		} catch (UserNameDuplicateExceptioin e) {
			model.addAttribute("msg", "新增失败，用户名已存在！");
			model.addAttribute("op", "create");
			model.addAttribute("user", new KrbUser());
			return "user/edit";
		}
		model.addAttribute("user", usr);
		model.addAttribute("msg", "新增成功");
		model.addAttribute("op", "create");
		return "user/edit";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userService.findOne(id));
		model.addAttribute("op", "修改");
		return "user/edit";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(KrbUser user, RedirectAttributes redirectAttributes, @PathVariable("id") Long id) {

		userService.updateUser(user, id);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/user";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String showDeleteForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userService.findOne(id));
		model.addAttribute("op", "删除");
		return "user/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		userService.deleteUser(id);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/user";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String showChangePasswordForm(Model model) {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated()) {
			String userName = (String) subject.getPrincipal();

			model.addAttribute("user", userService.findValidRequireDefRealm(userName));
			model.addAttribute("op", "修改密码");
			return "user/changePassword";
		} else {
			model.addAttribute("msg", "session过期或尚未登录,请重新登录。");
			return "/";
		}

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(String newPassword, RedirectAttributes redirectAttributes) {
		try {
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				String userName = (String) subject.getPrincipal();
				userService.changePassword(userService.findValidRequireDefRealm(userName).getId(), newPassword);
				redirectAttributes.addFlashAttribute("msg", "修改密码成功");
				return "redirect:/user";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		redirectAttributes.addFlashAttribute("msg", "修改密码失败");
		return "redirect:/user/changePassword";

	}
	@Autowired
	private IOAuthorizationApplicationsService oauthorizationApplicationsService;
	@RequestMapping(value = "/doOwnApprve/{owner}/{applicant}/{req}", produces = "application/json")
	@ResponseBody
	public boolean doOwnApprve(@PathVariable("owner") String id,@PathVariable("applicant") String applicant,@PathVariable("req") String req) {
		oauthorizationApplicationsService.updateOwnerApprove(id,applicant,req);
		return true;
	}

	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
	public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("user", userService.findOne(id));
		model.addAttribute("op", "修改密码");
		return "user/changePassword";
	}

	@RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
	public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
		userService.changePassword(id, newPassword);
		redirectAttributes.addFlashAttribute("msg", "修改密码成功");
		return "redirect:/user";
	}

}
