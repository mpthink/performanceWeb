package com.dell.petshow.webmonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dell.petshow.common.controller.SuperController;

@Controller
@RequestMapping("/webmonitor")
public class MonitorController extends SuperController {

	@RequestMapping(value = {"/", "/home"})
	public String index(Model model) {

		return "netact/monitor/home";
	}

}
