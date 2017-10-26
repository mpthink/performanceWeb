package com.dell.petshow.vtas.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.service.IProgramMapService;
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
@RequestMapping("/vtas/programMap")
public class ProgramMapController extends SuperController {

	@Autowired
	private IProgramMapService programMapService;

	@RequestMapping("/getAll")
	@ResponseBody
	public List<Map<String, Object>> getAll() {

		List<ProgramMap> programMaps = programMapService.selectAll();
		List<Map<String, Object>> listMap = Lists.transform(programMaps, new Function<ProgramMap, Map<String, Object>>() {
			@Override
			public Map<String, Object> apply(ProgramMap programMap) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", programMap.getMajorVersion());
				map.put("text", programMap.getProgram());
				return map;
			}
		});
		return listMap;
	}

}
