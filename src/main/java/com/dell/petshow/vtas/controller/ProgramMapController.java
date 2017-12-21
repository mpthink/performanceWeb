package com.dell.petshow.vtas.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.common.dto.NPResult;
import com.dell.petshow.vtas.entity.ProgramMap;
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


	/**
	 * 分页查询
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		return "vtas/programmap/list";
	}

	@RequestMapping("/getAllProgramMaps")
	@ResponseBody
	public String getAllProgramMaps() {
		return toJson(programMapService.selectList(null));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		return "vtas/programmap/add";
	}

	/**
	 * 执行新增
	 */
	@RequestMapping("/doAdd")
	public String doAdd(ProgramMap programMap) {
		programMapService.insert(programMap);
		return redirectTo("/vtas/programMap/list");
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete/{version:.+}")
	@ResponseBody
	public NPResult delete(@PathVariable String version) {
		Wrapper<ProgramMap> wrapper = new EntityWrapper<>();
		wrapper.eq("MAJOR_VERSION", version);
		programMapService.delete(wrapper);
		return new NPResult().success();
	}

	/**
	 * 编辑
	 */
	@RequestMapping("/edit/{version:.+}")
	public String edit(@PathVariable String version, Model model) {
		Wrapper<ProgramMap> wrapper = new EntityWrapper<>();
		wrapper.eq("MAJOR_VERSION", version);
		ProgramMap programMap = programMapService.selectOne(wrapper);
		model.addAttribute("programMap", programMap);
		return "vtas/programmap/edit";
	}

	/**
	 * 执行编辑
	 */
	@RequestMapping("/doEdit")
	public String doEdit(ProgramMap programMap, Model model) {
		programMapService.updateById(programMap);
		return redirectTo("/vtas/programMap/list");
	}
}
