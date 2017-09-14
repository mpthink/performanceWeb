package com.nokia.netactplus.system.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.nokia.netactplus.common.annotation.Log;
import com.nokia.netactplus.common.annotation.Permission;
import com.nokia.netactplus.common.controller.SuperController;
import com.nokia.netactplus.common.dto.NPResult;
import com.nokia.netactplus.system.entity.SysRole;
import com.nokia.netactplus.system.entity.SysRolePermission;
import com.nokia.netactplus.system.entity.vo.MenuVO;
import com.nokia.netactplus.system.service.ISysPermissionService;
import com.nokia.netactplus.system.service.ISysRolePermissionService;
import com.nokia.netactplus.system.service.ISysRoleService;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends SuperController {

	/**
	 * 角色服务
	 */
	@Autowired
	private ISysRoleService sysRoleService;
	/**
	 * 权限服务
	 */
	@Autowired
	private ISysPermissionService sysPermissionService;
	/**
	 * 角色权限
	 */
	@Autowired
	private ISysRolePermissionService sysRolePermissionService;


	/**
	 * 分页查询角色
	 */
	@Permission("010201")
	@RequestMapping("/list/{pageNumber}")
	public String list(@PathVariable Integer pageNumber, String search, Model model) {

		Page<SysRole> page = getPage(pageNumber);
		page.setOrderByField("gmtCreate");
		page.setAsc(false);
		// 查询分页
		EntityWrapper<SysRole> ew = new EntityWrapper<>();
		if (StringUtils.isNotBlank(search)) {
			ew.like("role_name", search);
			model.addAttribute("search", search);
		}
		Page<SysRole> pageData = sysRoleService.selectPage(page, ew);
		model.addAttribute("pageData", pageData);
		return "system/role/list";
	}

	/**
	 * 新增角色
	 */
	@Permission("010202")
	@RequestMapping("/add")
	public String add(Model model) {
		return "system/role/add";
	}

	/**
	 * 执行新增角色
	 */
	@Permission("010202")
	@Log("创建角色")
	@RequestMapping("/doAdd")
	public String doAdd(SysRole role) {
		role.setGmtCreate(new Date());
		sysRoleService.insert(role);
		return redirectTo("/system/role/list/1.html");

	}

	/**
	 * 删除角色
	 */
	@Permission("010203")
	@Log("删除角色")
	@RequestMapping("/delete")
	@ResponseBody
	public NPResult delete(String id) {
		sysRoleService.deleteById(id);
		return new NPResult().success();
	}

	/**
	 * 批量删除角色
	 */
	@Permission("010203")
	@Log("批量删除角色")
	@RequestMapping("/deleteBatch")
	@ResponseBody
	public NPResult deleteBatch(@RequestParam("id[]") List<String> ids) {
		sysRoleService.deleteBatchIds(ids);
		return new NPResult().success();
	}

	/**
	 * 编辑角色
	 */
	@Permission("010204")
	@RequestMapping("/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		SysRole sysRole = sysRoleService.selectById(id);
		model.addAttribute("sysRole", sysRole);
		return "system/role/edit";
	}

	/**
	 * 执行编辑角色
	 */
	@Permission("010204")
	@Log("编辑角色")
	@RequestMapping("/doEdit")
	public String doEdit(SysRole sysRole, Model model) {
		sysRoleService.updateById(sysRole);
		return redirectTo("/system/role/list/1.html");
	}

	/**
	 * 权限
	 */
	@Permission("010205")
	@RequestMapping("/auth/{roleId}")
	public String auth(@PathVariable Long roleId, Model model) {

		SysRole sysRole = sysRoleService.selectById(roleId);

		List<SysRolePermission> rolePermissions =
			sysRolePermissionService.selectList(new EntityWrapper<SysRolePermission>().addFilter("role_Id = {0}", roleId));
		List<Long> permissionIds = Lists.transform(rolePermissions, new Function<SysRolePermission, Long>() {
			@Override
			public Long apply(SysRolePermission input) {
				// TODO Auto-generated method stub
				return input.getPermId();
			}
		});

		List<MenuVO> treeMenuAllowAccesses = sysPermissionService.selectTreeMenuAllowAccessByMenuIdsAndPid(permissionIds, 0L);

		model.addAttribute("sysRole", sysRole);
		model.addAttribute("treeMenuAllowAccesses", treeMenuAllowAccesses);

		return "system/role/auth";
	}

	/**
	 * 权限
	 */
	@Permission("010205")
	@Log("角色分配权限")
	@RequestMapping("/doAuth")
	public String doAuth(Long roleId, Long[] permId, Model model) {
		sysRolePermissionService.addAuth(roleId, permId);
		model.addAttribute("info", "OK,授权成功,1分钟后生效  ~");
		this.auth(roleId, model);
		return "system/role/auth";
	}

}
