package com.dell.petshow.vtas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.service.IArrayInfoService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-11-01
 */
@Controller
@RequestMapping("/vtas/arrayInfo")
public class ArrayInfoController extends SuperController {

	@Autowired
	private IArrayInfoService arrayInfoService;

	@RequestMapping("/getArrayInfoWithUptime")
	@ResponseBody
	public String getArrayInfoWithUptime() {
		return toJson(arrayInfoService.getArrayWithUptime());
	}

	@RequestMapping("/configTest")
	public String viewConfigTest(Model model) {
		return "vtas/arrayinfo/configTest";
	}

	@RequestMapping("/editConfigTest")
	public String editConfigTest(Model model) {
		return "vtas/arrayinfo/editConfigTest";
	}

	@RequestMapping("/getArrayWithCurrentHours")
	@ResponseBody
	public String getArrayWithCurrentHours() {
		return toJson(arrayInfoService.getArrayWithCurrentHours());
	}
}
