package com.nokia.netactplus.system.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.nokia.netactplus.common.annotation.Permission;
import com.nokia.netactplus.common.controller.SuperController;
import com.nokia.netactplus.system.entity.SysSetting;
import com.nokia.netactplus.system.service.ISysSettingService;

/**
 * <p>
 * 系统配置表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/system/setting")
public class SysSettingController extends SuperController {

	@Autowired
	private ISysSettingService sysSettingService;

	@Permission("010501")
	@RequestMapping("/page")
	public String list(Model model) {
		List<SysSetting> settings = sysSettingService.selectList(new EntityWrapper<SysSetting>().orderBy("sort", true));
		model.addAttribute("list", settings);
		return "system/setting/page";
	}

	@Permission("010501")
	@RequestMapping("/doEdit")
	public String doEdit(Long[] id, String[] sysValue, Model model) {
		List<SysSetting> settings = new ArrayList<>();
		if (ArrayUtils.isNotEmpty(id)) {
			for (int i = 0; i < id.length; i++) {
				SysSetting setting = new SysSetting();
				setting.setId(id[i]);
				setting.setSysValue(sysValue[i]);
				settings.add(setting);
			}
			sysSettingService.updateBatchById(settings);
		}
		return redirectTo("/system/setting/page.html");
	}
}
