package com.zylu.handline.controller;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zylu.handline.service.IUserService;

@Controller
@RequestMapping("/initialize")
@Scope("request")
public class CommonController {

	@Resource(name = "userService")
	private IUserService userService;
	
	@RequestMapping("/init")
	public String init () {
		return "test";
	}
}
