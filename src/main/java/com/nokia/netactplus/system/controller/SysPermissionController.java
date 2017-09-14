package com.nokia.netactplus.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.nokia.netactplus.common.annotation.Permission;
import com.nokia.netactplus.common.controller.SuperController;
import com.nokia.netactplus.common.dto.NPResult;
import com.nokia.netactplus.system.entity.SysPermission;
import com.nokia.netactplus.system.service.ISysPermissionService;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/system/perm")
public class SysPermissionController extends SuperController {

	/**
	 * 权限服务
	 */
	@Autowired
	private ISysPermissionService sysPermissionService;

	/**
	 * 分页查询权限
	 */
	@Permission("010301")
	@RequestMapping("/list/{pageNumber}")
	public String list(@PathVariable Integer pageNumber, String search, Model model) {

		Page<SysPermission> page = getPage(pageNumber);
		page.setOrderByField("perm_code");
		page.setAsc(true);
		EntityWrapper<SysPermission> ew = new EntityWrapper<>();
		if (StringUtils.isNoneBlank(search)) {
			ew.like("perm_name", search);
			model.addAttribute("search", search);
		}

		Page<SysPermission> pageData = sysPermissionService.selectPage(page, ew);
		for (SysPermission permission : pageData.getRecords()) {
			if (permission.getPid() == 0 && permission.getPermType() == 0) {
				permission.setPermName(StringUtils.join("<i class='fa fa-folder-open'></i> ", permission.getPermName()));
			} else if (permission.getPid() != 0 && permission.getPermType() == 0) {
				permission.setPermName(StringUtils.join("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-file'></i> ", permission.getPermName()));
			} else {
				permission.setPermName(StringUtils.join(
					"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class='fa fa-file'></i> ", permission.getPermName()));
			}
		}
		model.addAttribute("pageData", pageData);
		return "system/perm/list";
	}

	/**
	 * 增加权限
	 */
	@Permission("010302")
	@RequestMapping("/add")
	public String add(Model model) {
		EntityWrapper<SysPermission> wrapper = new EntityWrapper<>();
		wrapper.eq("pid", 0);
		List<SysPermission> permList = sysPermissionService.selectList(wrapper);
		model.addAttribute("permList", permList);
		return "system/perm/add";
	}

	/**
	 * 执行添加
	 */
	@Permission("010302")
	@RequestMapping("/doAdd/{permType}")
	public String doAdd(SysPermission sysPermission, @PathVariable("permType") Integer permType, Model model) {
		sysPermission.setPermType(permType);
		sysPermission.insert();
		return redirectTo("/system/perm/list/1.html");
	}

	/**
	 * 编辑权限
	 */
	@Permission("010304")
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, Model model) {
		SysPermission sysPermission = sysPermissionService.selectById(id);
		model.addAttribute("sysPermission", sysPermission);

		if (sysPermission.getPid() != 0) {
			Wrapper<SysPermission> wrapper = new EntityWrapper<>();
			wrapper.orderBy("perm_code", true);
			wrapper.eq("pid", 0);
			List<SysPermission> topList = sysPermissionService.selectList(wrapper);
			model.addAttribute("topList", topList);
		}

		if (sysPermission.getPermType() == 1) {
			SysPermission parentPerm = sysPermissionService.selectById(sysPermission.getPid());
			model.addAttribute("parentPerm", parentPerm);
		}

		return "system/perm/edit";
	}

	/**
	 * 执行编辑
	 */
	@Permission("010304")
	@RequestMapping("/doEdit")
	public String doEdit(SysPermission sysPermission, Model model) {
		sysPermissionService.updateById(sysPermission);
		return redirectTo("/system/perm/list/1.html");
	}

	/**
	 * 删除权限
	 */
	@Permission("010304")
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public NPResult doDelete(@PathVariable("id") Long id) {
		sysPermissionService.deleteById(id);
		return new NPResult().success();
	}

	/**
	 * 根据父节点获取子菜单
	 */
	@RequestMapping("/getperms/{pid}")
	@ResponseBody
	public NPResult getperms(@PathVariable("pid") Long pid) {
		EntityWrapper<SysPermission> ew = new EntityWrapper<>();
		ew.orderBy("sort");
		ew.where("pid = {0} ", pid);
		List<SysPermission> permissions = sysPermissionService.selectList(ew);

		List<Map<String, Object>> listMap = Lists.transform(permissions, new Function<SysPermission, Map<String, Object>>() {
			@Override
			public Map<String, Object> apply(SysPermission perm) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", perm.getId().toString());
				map.put("text", perm.getPermName());
				return map;
			}
		});
		return new NPResult().success(listMap);
	}

}
