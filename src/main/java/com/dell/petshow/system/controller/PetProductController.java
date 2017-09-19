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
import com.dell.petshow.system.entity.PetProduct;
import com.dell.petshow.system.entity.PetProductClass;
import com.dell.petshow.system.service.IPetProductClassService;
import com.dell.petshow.system.service.IPetProductService;

/**
 * <p>
 * 产品表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
@Controller
@RequestMapping("/pet/product")
public class PetProductController extends SuperController {

	@Autowired
	private IPetProductService productService;
	@Autowired
	private IPetProductClassService petProductClassService;

	@Permission("010701")
	@RequestMapping("/list")
	public String list(Model model) {
		return "pet/product/list";
	}

	@RequestMapping("/getall")
	@ResponseBody
	public String getAll() {
		return toJson(productService.selectWithClassName());
	}

	/**
	 * 新增项目
	 */
	@Permission("010702")
	@RequestMapping("/add")
	public String add(Model model) {
		EntityWrapper<PetProductClass> wrapper = new EntityWrapper<>();
		wrapper.orderBy("pid,gmt_create");
		List<PetProductClass> classList = petProductClassService.selectList(wrapper);
		model.addAttribute("classList", classList);
		return "pet/product/add";
	}

	/**
	 * 执行新增项目
	 */
	@Permission("010702")
	@Log("创建项目")
	@RequestMapping("/doAdd")
	public String doAdd(PetProduct petProduct) {
		petProduct.setGmtCreate(new Date());
		productService.insert(petProduct);
		return redirectTo("/pet/product/list");

	}

	/**
	 * 删除项目
	 */
	@Permission("010703")
	@Log("删除项目")
	@RequestMapping("/delete")
	@ResponseBody
	public NPResult delete(@RequestParam("id") String id) {
		productService.deleteById(id);
		return new NPResult().success();
	}

	/**
	 * 批量删除项目
	 */
	@Permission("010703")
	@Log("批量删除项目")
	@RequestMapping("/deleteBatch")
	@ResponseBody
	public NPResult deleteBatch(@RequestParam(value = "ids[]") String ids) {
		List<String> list = Arrays.asList(ids);
		productService.deleteBatchIds(list);
		return new NPResult().success();
	}

	/**
	 * 编辑项目
	 */
	@Permission("010704")
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		PetProduct petproduct = productService.selectById(id);
		model.addAttribute("petproduct", petproduct);
		EntityWrapper<PetProductClass> wrapper = new EntityWrapper<>();
		wrapper.orderBy("pid,gmt_create");
		List<PetProductClass> classList = petProductClassService.selectList(wrapper);
		model.addAttribute("classList", classList);
		return "pet/product/edit";
	}

	/**
	 * 执行编辑项目
	 */
	@Permission("010204")
	@Log("编辑项目")
	@RequestMapping("/doEdit")
	public String doEdit(PetProduct petProduct, Model model) {
		productService.updateById(petProduct);
		return redirectTo("/pet/product/list");
	}
}
