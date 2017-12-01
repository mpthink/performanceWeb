package com.dell.petshow.vtas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.plugins.Page;
import com.dell.petshow.common.annotation.Log;
import com.dell.petshow.common.annotation.Permission;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.common.dto.NPResult;
import com.dell.petshow.vtas.entity.VtasFiles;
import com.dell.petshow.vtas.service.IVtasFilesService;

/**
 * <p>
 * 文件管理表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-12-01
 */
@Controller
@RequestMapping("/vtas/file")
public class VtasFilesController extends SuperController {

	@Autowired
	private IVtasFilesService vtasFileService;


	@RequestMapping("/viewFile/{fileName:.+}")
	public String viewFile(@PathVariable("fileName") String fileName, Model model) {
		model.addAttribute("fileName", fileName);
		return "vtas/file/viewExcel";
	}

	@RequestMapping("/editFile/{fileName:.+}")
	public String editFile(@PathVariable("fileName") String fileName, Model model) {
		model.addAttribute("fileName", fileName);
		return "vtas/file/editExcel";
	}

	@Permission("060101")
	@RequestMapping("/list/{pageNumber}")
	public String list(@PathVariable Integer pageNumber, String search, Model model) {

		Page<VtasFiles> page = getPage(pageNumber);
		Page<VtasFiles> pageData = vtasFileService.selectPage(page);
		model.addAttribute("pageData", pageData);
		return "vtas/file/list";
	}

	@Permission("060102")
	@RequestMapping("/add")
	public String add(Model model) {
		return "vtas/file/add";
	}

	@Permission("060102")
	@Log("Create File")
	@RequestMapping("/doAdd")
	public String doAdd(VtasFiles file, String[] roleId) {
		vtasFileService.insert(file);
		return redirectTo("/vtas/file/list/1.html");
	}

	@Permission("060103")
	@Log("delete file")
	@RequestMapping("/delete")
	@ResponseBody
	public NPResult delete(String id) {
		vtasFileService.deleteById(id);
		return new NPResult().success();
	}

	@Permission("060104")
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		VtasFiles file = vtasFileService.selectById(id);

		model.addAttribute("file", file);
		return "vtas/file/edit";
	}

	@Permission("060104")
	@Log("edit file")
	@RequestMapping("/doEdit")
	public String doEdit(VtasFiles file, Model model) {
		vtasFileService.updateById(file);
		return redirectTo("/vtas/file/list/1.html");
	}
}
