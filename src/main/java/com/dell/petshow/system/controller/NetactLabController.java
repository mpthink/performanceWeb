package com.dell.petshow.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.common.dto.NPResult;
import com.dell.petshow.system.entity.UserLab;
import com.dell.petshow.system.service.INetactLabService;
import com.dell.petshow.system.service.IUserLabService;

/**
 * <p>
 * Lab 信息表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/system/lab")
public class NetactLabController extends SuperController {

	@Autowired
	private INetactLabService netactLabService;

	@Autowired
	private IUserLabService userLabService;

	@RequestMapping("list")
	public String listAll() {
		return "netact/lab/list";
	}

	@RequestMapping("mylist")
	public String listMyLab() {
		return "netact/lab/mylist";
	}

	@RequestMapping("/getall")
	@ResponseBody
	public String getAll() {
		return toJson(netactLabService.selectList(null));
	}

	@RequestMapping("/getmylist")
	@ResponseBody
	public String getMyLab() {
		Long userId = SSOHelper.getToken(request).getId();
		return toJson(netactLabService.selectByUserId(userId));
	}

	@RequestMapping("/addFavorite/{labId}")
	@ResponseBody
	public String addFavorite(@PathVariable("labId") Long labId) {
		Long userId = SSOHelper.getToken(request).getId();
		EntityWrapper<UserLab> wrapper = new EntityWrapper<>();
		wrapper.eq("user_id", userId);
		wrapper.eq("lab_id", labId);
		UserLab temp = userLabService.selectOne(wrapper);
		if (null == temp) {
			UserLab userLab = new UserLab();
			userLab.setUserId(userId);
			userLab.setLabId(labId);
			userLab.insert();
			return toJson(new NPResult().success());
		} else {
			return toJson(new NPResult().failure("Has added to favorite"));
		}
	}

	@RequestMapping("/removeFavorite/{labId}")
	@ResponseBody
	public String removeFavorite(@PathVariable("labId") Long labId) {
		Long userId = SSOHelper.getToken(request).getId();
		EntityWrapper<UserLab> wrapper = new EntityWrapper<>();
		wrapper.eq("user_id", userId);
		wrapper.eq("lab_id", labId);
		UserLab temp = userLabService.selectOne(wrapper);
		if (null == temp) {
			return toJson(new NPResult().success());
		} else {
			if (userLabService.delete(wrapper)) {
				return toJson(new NPResult().success());
			} else {
				return toJson(new NPResult().failure("Remove from favorite failed!"));
			}
		}
	}
}
