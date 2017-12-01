package com.dell.petshow.system.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.dell.petshow.common.annotation.Permission;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.system.entity.SysLog;
import com.dell.petshow.system.service.ISysLogService;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/system/log")
public class SysLogController extends SuperController {

	@Autowired
	private ISysLogService sysLogService;

	/**
	 * 分页查询日志
	 */
	@Permission("010701")
	@RequestMapping("/list/{pageNumber}")
	public String list(@PathVariable("pageNumber") Integer pageNumber, String search, String dateRange, Model model) {
		Page<SysLog> page = getPage(pageNumber);
		page.setOrderByField("gmtCreate");
		page.setAsc(false);
		// 查询分页
		Wrapper<SysLog> wrapper = new EntityWrapper<>();
		if (StringUtils.isNotBlank(search)) {
			wrapper.where("user_name like CONCAT('\'%'\',{0},'\'%'\')", search).or("title like CONCAT('\'%'\',{0},'\'%'\')", search);
			model.addAttribute("search", search);
		}

		//日期查询
		if (StringUtils.isNotBlank(dateRange)) {
			model.addAttribute("dateRange", dateRange);
			String[] dateranges = StringUtils.split(dateRange, "-");
			wrapper.where(" gmt_create >= {0}", dateranges[0].trim().replaceAll("/", "-") + " 00:00:00");
			wrapper.where(" gmt_create <= {0}", dateranges[1].trim().replaceAll("/", "-") + " 23:59:59");
		}
		Page<SysLog> paegData = sysLogService.selectPage(page, wrapper);
		model.addAttribute("pageData", paegData);
		return "system/log/list";
	}

	/**
	 * 获取参数
	 */
	@RequestMapping("/params/{id}")
	@ResponseBody
	public String params(@PathVariable String id, Model model) {
		SysLog sysLog = sysLogService.selectById(id);
		return sysLog.getParams();
	}

}
