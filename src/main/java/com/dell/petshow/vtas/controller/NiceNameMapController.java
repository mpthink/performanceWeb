package com.dell.petshow.vtas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.entity.NiceNameMap;
import com.dell.petshow.vtas.service.INiceNameMapService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Controller
@RequestMapping("/vtas/niceNameMap")
public class NiceNameMapController extends SuperController {

	@Autowired
	private INiceNameMapService niceNameMapService;

	@RequestMapping("/getNiceNameByProgramAndArray/{bigVersion}/{arrayName}")
	@ResponseBody
	public List<Map<String, Object>> getNiceNameByProgramAndArray(@PathVariable("bigVersion") String bigVersion,
		@PathVariable("arrayName") String arrayName) {
		List<NiceNameMap> nameList = niceNameMapService.selectNiceNameByVersionAndArray(bigVersion, arrayName);
		List<Map<String, Object>> listMap = Lists.transform(nameList, new Function<NiceNameMap, Map<String, Object>>() {
			@Override
			public Map<String, Object> apply(NiceNameMap niceNameMap) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", niceNameMap.getVersion());
				map.put("text", niceNameMap.getNiceName());
				return map;
			}
		});
		return listMap;
	}
}
