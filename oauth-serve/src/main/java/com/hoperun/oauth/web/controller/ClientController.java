package com.hoperun.oauth.web.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hoperun.oauth.entity.constants.AuthorizationStatus;
import com.hoperun.oauth.entity.po.AuthorizationApplications;
import com.hoperun.oauth.entity.po.AuthorizationRequierment;
import com.hoperun.oauth.entity.po.KrbRealm;
import com.hoperun.oauth.entity.po.KrbUser;
import com.hoperun.oauth.entity.po.OauthClient;
import com.hoperun.oauth.service.IKrbPrincipleService;
import com.hoperun.oauth.service.IKrbRealmService;
import com.hoperun.oauth.service.IOAuthClientService;
import com.hoperun.oauth.service.IOAuthorizationApplicationsService;
import com.hoperun.oauth.utils.Page;
import com.hoperun.oauth.utils.PageUtil;
import com.hoperun.oauth.utils.StringUtil;
import com.hoperun.oauth.utils.XmlAuthorizationUtil;

@Controller
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private IOAuthClientService clientService;
	
	@Autowired
	IKrbPrincipleService krbPrincipleService;
	@Autowired
	IKrbRealmService krbRealmService;
	@Autowired
	IOAuthorizationApplicationsService oauthorizationApplicationsService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model,HttpServletRequest request) {
		Page<OauthClient> page = new Page<OauthClient>(PageUtil.PAGE_SIZE);
		PageUtil.init(page, request);
		model.addAttribute("page", clientService.findAll(page));
		return "client/list";
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String showCreateForm(Model model) {
		model.addAttribute("client", new OauthClient());
		model.addAttribute("op", "新增");
		return "client/edit";
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(MultipartFile metaFile,OauthClient client, RedirectAttributes redirectAttributes) {
		if(client==null||StringUtil.isEmpty(client.getClientName())){
			redirectAttributes.addFlashAttribute("msg", "client 名称不能为空");
			return "redirect:/client/create";
		}
		
		Subject subject = SecurityUtils.getSubject();
    	String userName = (String)subject.getPrincipal();
    	
    	KrbUser user = krbPrincipleService.findValidRequireDefRealm(userName);
		if(metaFile==null){
			redirectAttributes.addFlashAttribute("msg", "失败，只接受xml格式授权声明文件");
			return "redirect:/client/create";
		}else{
			
			String fileName = metaFile.getOriginalFilename();
			String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
			if(!prefix.toUpperCase().equals("XML")){
				redirectAttributes.addFlashAttribute("msg", "失败，只接受xml格式授权声明文件");
				return "redirect:/client/create";
			}
			File tmp = new File("temp.xml");
			
			try {
				metaFile.transferTo(tmp);
				AuthorizationRequierment ar = XmlAuthorizationUtil.xml2Bean(tmp);
				if(ar!=null){
					client.setAr(ar);
					ar.setClient(client);
					ar.setResourceOwner(user);
				}
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
    	client.setOwner(user);
    	KrbRealm kr = client.getClientRealm();
    	if(kr!=null){}
    		client.setClientRealm(krbRealmService.findWithRealm(kr.getRealm()));
		clientService.save(client);
		redirectAttributes.addFlashAttribute("msg", "新增成功");
		return "redirect:/client";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("client", clientService.findById(id));
		model.addAttribute("op", "修改");
		return "client/edit";
	}

	@RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
	public String update(OauthClient client, RedirectAttributes redirectAttributes) {
		clientService.update(client);
		redirectAttributes.addFlashAttribute("msg", "修改成功");
		return "redirect:/client";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public String showDeleteForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("client", clientService.findById(id));
		model.addAttribute("op", "删除");
		return "client/edit";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		clientService.delete(id);
		redirectAttributes.addFlashAttribute("msg", "删除成功");
		return "redirect:/client";
	}
	
	@RequestMapping(value="/approveRequests",method = RequestMethod.GET)
	public String listAreqs(Model model) {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		List<AuthorizationApplications> list = oauthorizationApplicationsService.findWaitForApproveByResourceOwner(username);
		model.addAttribute("reqList", list);
		return "client/premisionReqlist";
	}
	
	@RequestMapping(value="/{id}/deal",method = RequestMethod.GET)
	public String listAreqs(@PathVariable("id") Long id,Model model,@RequestParam(value="result") int result) {
		Subject subject = SecurityUtils.getSubject();
		String username = (String) subject.getPrincipal();
		oauthorizationApplicationsService.updateStatusById(id,username,AuthorizationStatus.fromElem(result));
		return "redirect:/client/approveRequests";
	}

}
