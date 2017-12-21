package com.dell.petshow.vtas.controller;

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
import com.dell.petshow.vtas.entity.ArrayInfo;
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



	/**
	 * 分页查询
	 */
	@RequestMapping("/list")
	public String list(Model model) {
		return "vtas/arrayinfo/list";
	}

	@RequestMapping("/getAllArrayInfo")
	@ResponseBody
	public String getAllArrayInfo() {
		Wrapper<ArrayInfo> wrapper = new EntityWrapper<>();
		wrapper.orderBy("ARRAY_NAME");
		return toJson(arrayInfoService.selectList(wrapper));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public String add(Model model) {
		return "vtas/arrayinfo/add";
	}

	/**
	 * 执行新增
	 */
	@RequestMapping("/doAdd")
	public String doAdd(ArrayInfo arrayInfo) {
		arrayInfoService.insert(arrayInfo);
		return redirectTo("/vtas/arrayInfo/list");
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete/{arrayName:.+}")
	@ResponseBody
	public NPResult delete(@PathVariable String arrayName) {
		Wrapper<ArrayInfo> wrapper = new EntityWrapper<>();
		wrapper.eq("ARRAY_NAME", arrayName);
		arrayInfoService.delete(wrapper);
		return new NPResult().success();
	}

	/**
	 * 编辑
	 */
	@RequestMapping("/edit/{arrayName:.+}")
	public String edit(@PathVariable String arrayName, Model model) {
		Wrapper<ArrayInfo> wrapper = new EntityWrapper<>();
		wrapper.eq("ARRAY_NAME", arrayName);
		ArrayInfo arrayInfo = arrayInfoService.selectOne(wrapper);
		model.addAttribute("arrayInfo", arrayInfo);
		return "vtas/arrayinfo/edit";
	}

	/**
	 * 执行编辑
	 */
	@RequestMapping("/doEdit")
	public String doEdit(ArrayInfo arrayInfo, Model model) {
		arrayInfoService.updateById(arrayInfo);
		return redirectTo("/vtas/arrayInfo/list");
	}

}
