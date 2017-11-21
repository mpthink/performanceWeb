package com.dell.petshow.common.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.Token;
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
		Token token = SSOHelper.getToken(request);
		if (token == null) {
			SSOToken st = new SSOToken();
			st.setId(999999999999999999L);
			st.setData("Visitor");
			SSOHelper.setSSOCookie(request, response, st, true);
		}
		return "index";
	}

}
