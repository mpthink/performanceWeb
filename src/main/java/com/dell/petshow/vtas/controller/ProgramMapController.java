package com.dell.petshow.vtas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.vtas.service.IProgramMapService;

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
		return programMapService.selectAllForSelect2(true);
	}

}
