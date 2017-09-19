package com.dell.petshow.system.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dell.petshow.common.annotation.Log;
import com.dell.petshow.common.annotation.Permission;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.common.dto.NPResult;
import com.dell.petshow.system.entity.PetProductClass;
import com.dell.petshow.system.service.IPetProductClassService;

/**
 * <p>
 * 产品分类表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
@Controller
@RequestMapping("/pet/productclass")
public class PetProductClassController extends SuperController {

	/**
	 * 分类服务
	 */
	@Autowired
	private IPetProductClassService petProductClassService;

	@Permission("010701")
	@RequestMapping("/list")
	public String list(Model model) {
		return "pet/productclass/list";
	}

	@RequestMapping("/getall")
	@ResponseBody
	public String getAll() {
		return toJson(petProductClassService.selectWithPclassName());
	}

	/**
	 * 新增分类
	 */
	@Permission("010702")
	@RequestMapping("/add")
	public String add(Model model) {
		EntityWrapper<PetProductClass> wrapper = new EntityWrapper<>();
		wrapper.orderBy("pid,gmt_create");
		List<PetProductClass> classList = petProductClassService.selectList(wrapper);
		model.addAttribute("classList", classList);
		return "pet/productclass/add";
	}

	/**
	 * 执行新增分类
	 */
	@Permission("010702")
	@Log("创建分类")
	@RequestMapping("/doAdd")
	public String doAdd(PetProductClass petProductClass) {
		petProductClass.setGmtCreate(new Date());
		petProductClassService.insert(petProductClass);
		return redirectTo("/pet/productclass/list");

	}

	/**
	 * 删除分类
	 */
	@Permission("010703")
	@Log("删除分类")
	@RequestMapping("/delete")
	@ResponseBody
	public NPResult delete(@RequestParam("id") String id) {
		petProductClassService.deleteById(id);
		return new NPResult().success();
	}

	/**
	 * 批量删除分类
	 */
	@Permission("010703")
	@Log("批量删除分类")
	@RequestMapping("/deleteBatch")
	@ResponseBody
	public NPResult deleteBatch(@RequestParam(value = "ids[]") String ids) {
		List<String> list = Arrays.asList(ids);
		petProductClassService.deleteBatchIds(list);
		return new NPResult().success();
	}

	/**
	 * 编辑分类
	 */
	@Permission("010704")
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		PetProductClass petProductClass = petProductClassService.selectById(id);
		model.addAttribute("petProductClass", petProductClass);
		EntityWrapper<PetProductClass> wrapper = new EntityWrapper<>();
		wrapper.orderBy("pid,gmt_create");
		List<PetProductClass> classList = petProductClassService.selectList(wrapper);
		model.addAttribute("classList", classList);
		return "pet/productclass/edit";
	}

	/**
	 * 执行编辑分类
	 */
	@Permission("010204")
	@Log("编辑分类")
	@RequestMapping("/doEdit")
	public String doEdit(PetProductClass petProductClass, Model model) {
		petProductClassService.updateById(petProductClass);
		return redirectTo("/pet/productclass/list");
	}

}
