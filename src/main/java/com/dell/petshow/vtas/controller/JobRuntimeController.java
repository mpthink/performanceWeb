package com.dell.petshow.vtas.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.service.IJobRuntimeService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Controller
@RequestMapping("/vtas/jobRuntime")
public class JobRuntimeController extends SuperController {

	@Autowired
	private IJobRuntimeService jobRuntimeService;

	@RequestMapping("/vsBuild")
	public String index(Model model) {
		return "vtas/jobruntime/vsbuild";
	}

	@RequestMapping("/getArrayByProgram/{bigVersion}/")
	@ResponseBody
	public List<Map<String, String>> getArrayByProgram(@PathVariable("bigVersion") String bigVersion) {
		if (bigVersion == "" || bigVersion == null) {
			return null;
		}
		List<String> arrayList = jobRuntimeService.selectDistinctArrayListByProgram(bigVersion);
		List<Map<String, String>> listMap = new ArrayList<>();
		for (String arrayName : arrayList) {
			Map<String, String> map = new HashMap<>();
			map.put("id", arrayName);
			map.put("text", arrayName);
			listMap.add(map);
		}
		return listMap;
	}

}
