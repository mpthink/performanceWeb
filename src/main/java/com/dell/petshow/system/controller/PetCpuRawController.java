package com.dell.petshow.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.system.entity.PetCpuRaw;
import com.dell.petshow.system.service.IPetCpuRawService;

/**
 * <p>
 * CPU 性能原始数据表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
@Controller
@RequestMapping("/pet/cpuRaw")
public class PetCpuRawController extends SuperController {
	@Autowired
	private IPetCpuRawService petCpuRawService;


	@RequestMapping("/getData/{id}/{begin}/{end}")
	@ResponseBody
	public String getData(@PathVariable String id, @PathVariable String begin, @PathVariable String end) {
		EntityWrapper<PetCpuRaw> ew = new EntityWrapper<>();
		ew.ge("gmt_generate", begin);
		ew.le("gmt_generate", end);
		return toJson(petCpuRawService.selectList(ew));
	}
}
