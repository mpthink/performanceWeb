package com.dell.petshow.common.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.mybatisplus.plugins.Page;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.system.entity.SysUser;


public class NPResultTest extends SuperController {
	SuperController controller = new SuperController();

	public static void main(String[] args) {
		Page<SysUser> data = new Page<>();
		NPResult test = new NPResult();
		test.success(data);
		System.out.println(test.getData());

		TypeUtils.compatibleWithJavaBean = true;
		System.out.println(JSON.toJSONString(test, SerializerFeature.BrowserCompatible));

	}

}
