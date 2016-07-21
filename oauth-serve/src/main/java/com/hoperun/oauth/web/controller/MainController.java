package com.hoperun.oauth.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hoperun.oauth.utils.Utlis;

@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping(value = "/index")
    public String index() {
        return "/main/index";
    }

    @RequestMapping(value = "/welcome")
    public String welcome(Model model) {
    	model.addAttribute("pid", Utlis.getPid());
        return "/main/welcome";
    }

}