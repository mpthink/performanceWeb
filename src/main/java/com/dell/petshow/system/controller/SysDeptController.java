package com.dell.petshow.system.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dell.petshow.common.annotation.Log;
import com.dell.petshow.common.annotation.Permission;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.common.dto.NPResult;
import com.dell.petshow.system.entity.SysDept;
import com.dell.petshow.system.service.ISysDeptService;


/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends SuperController {


	@Autowired
	private ISysDeptService sysDeptService;

	/**
	 * 分页查询部门
	 */
	@Permission("010601")
	@RequestMapping("/list/{pageNumber}")
	public String list(@PathVariable Integer pageNumber, String search, Model model) {

		Page<SysDept> page = getPage(pageNumber);
		// 查询分页
		EntityWrapper<SysDept> ew = new EntityWrapper<SysDept>();
		if (StringUtils.isNotBlank(search)) {
			ew.like("dept_name", search);
			model.addAttribute("search", search);
		}
		Page<SysDept> pageData = sysDeptService.selectPage(page, ew);
		model.addAttribute("pageData", pageData);
		return "system/dept/list";
	}

	/**
	 * 新增部门
	 */
	@Permission("010602")
	@RequestMapping("/add")
	public String add(Model model) {
		return "system/dept/add";
	}

	/**
	 * 执行新增
	 */
	@Permission("010602")
	@Log("创建部门")
	@RequestMapping("/doAdd")
	public String doAdd(SysDept dept, String[] roleId) {

		sysDeptService.insert(dept);
		return redirectTo("/system/dept/list/1.html");
	}

	/**
	 * 删除部门
	 */
	@Permission("010603")
	@Log("删除部门")
	@RequestMapping("/delete")
	@ResponseBody
	public NPResult delete(String id) {
		sysDeptService.deleteById(id);
		return new NPResult().success();
	}

	/**
	 * 编辑部门
	 */
	@Permission("010604")
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		SysDept dept = sysDeptService.selectById(id);

		model.addAttribute("dept", dept);
		return "system/dept/edit";
	}

	/**
	 * 执行编辑
	 */
	@Permission("010604")
	@Log("编辑部门")
	@RequestMapping("/doEdit")
	public String doEdit(SysDept dept, Model model) {
		sysDeptService.updateById(dept);
		return redirectTo("/system/dept/list/1.html");
	}
}
