package com.dell.petshow.vtas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.service.IArrayConfigurationService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2018-01-02
 */
@Controller
@RequestMapping("/vtas/arrayConfiguration")
public class ArrayConfigurationController extends SuperController {

	@Autowired
	private IArrayConfigurationService arrayConfigurationService;

	/**
	 * 默认展示当前最新数据
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		return "vtas/arrayConfiguration/list";
	}

	@RequestMapping("/getLatestArrayConfiguration")
	@ResponseBody
	public String getLatestArrayConfiguration() {
		return toJson(arrayConfigurationService.selectLatestArrayConfiguration());
	}

	@RequestMapping("/getArrayConfigurationByTime/{time}")
	@ResponseBody
	public String getArrayConfigurationByTime(@PathVariable("time") String time) {
		return toJson(arrayConfigurationService.getArrayConfigurationByTime(time));
	}

	@RequestMapping("/getPollTime")
	@ResponseBody
	public List<Map<String, Object>> getPollTime() {
		return arrayConfigurationService.selectTimeForSelect2();
	}
}
