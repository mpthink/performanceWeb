package com.nokia.netactplus.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;

@Controller
@RequestMapping("/")
public class IndexController extends SuperController {

	@Permission(action = Action.Skip)
	@RequestMapping(value = {"/index"})
	public String index(Model model) {
		return "index";
	}

}
