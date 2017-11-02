package com.dell.petshow.common.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Permission;
import com.dell.petshow.vtas.service.IJobRuntimeService;

@Controller
@RequestMapping("/")
public class IndexController extends SuperController {

	@Autowired
	private IJobRuntimeService jobRuntimeService;

	@Permission(action = Action.Skip)
	@RequestMapping(value = {"/index", "/"})
	public String index(Model model) {
		List<Map<String, Object>> listOne = jobRuntimeService.getArrayNumForAllPrograms();
		model.addAttribute("listOne", listOne);
		return "index";
	}

}
