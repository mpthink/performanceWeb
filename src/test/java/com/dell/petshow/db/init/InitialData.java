package com.dell.petshow.db.init;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dell.petshow.system.entity.SysPermission;
import com.dell.petshow.system.entity.SysRole;
import com.dell.petshow.system.entity.SysRolePermission;
import com.dell.petshow.system.entity.SysSetting;
import com.dell.petshow.system.entity.SysUser;
import com.dell.petshow.system.entity.SysUserRole;
import com.dell.petshow.system.mapper.SysPermissionMapper;
import com.dell.petshow.system.mapper.SysRoleMapper;
import com.dell.petshow.system.mapper.SysRolePermissionMapper;
import com.dell.petshow.system.mapper.SysUserMapper;
import com.dell.petshow.system.mapper.SysUserRoleMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class InitialData {

	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;


	@Test
	public void initData() {

		initFirstUser();
		initRoleData();
		initUserRole();
		initSystemMenus();
		initRolePermission();
		initSettings();

	}

	private void initSettings() {
		createSettings("systemName", "系统名称", "PetShow", 0);
		createSettings("systemSubName", "系统简称", "D+", 1);
		createSettings("bottomCopyright", "许可说明", "Copyright © 2017 Dell. All rights reserved.", 2);
	}

	private void createSettings(String sysKey, String sysName, String sysValue, int sort) {
		SysSetting setting = new SysSetting();
		setting.setSysKey(sysKey);
		setting.setSysName(sysName);
		setting.setSysValue(sysValue);
		setting.setSort(sort);
		setting.setGmtCreate(new Date());
		setting.insert();
	}

	private void initFirstUser() {
		String userName = "paul.p.ma@emc.com";
		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		SysUser temp = sysUserMapper.selectOne(sysUser);
		if (temp == null) {
			sysUser.setEmail(userName);
			sysUser.setPassword("123456");
			sysUser.setGmtCreate(new Date());
			sysUser.setAvatar("/AdminLTE/img/luo.jpg");
			sysUser.insert();
		}
	}

	private void initRoleData() {
		SysRole adminRole = new SysRole();
		adminRole.setRoleName("系统管理员");
		if (sysRoleMapper.selectOne(adminRole) == null) {
			adminRole.insert();
		}

		SysRole generalRole = new SysRole();
		generalRole.setRoleName("普通会员");
		if (sysRoleMapper.selectOne(generalRole) == null) {
			generalRole.insert();
		}
	}

	private void initUserRole() {
		String userName = "paul.p.ma@emc.com";
		SysUser sysUser = new SysUser();
		sysUser.setUserName(userName);
		sysUser = sysUserMapper.selectOne(sysUser);
		if (sysUser != null) {
			SysRole role = new SysRole();
			role.setRoleName("系统管理员");
			role = sysRoleMapper.selectOne(role);
			if (role != null) {
				SysUserRole uRole = new SysUserRole();
				uRole.setRoleId(role.getId());
				uRole.setUserId(sysUser.getId());
				SysUserRole tempUR = sysUserRoleMapper.selectOne(uRole);
				if (tempUR == null) {
					uRole.insert();
				}
			}
		}
	}

	private void initSystemMenus() {
		SysPermission temp = new SysPermission();
		temp.setPermName("系统管理");
		SysPermission perm = sysPermissionMapper.selectOne(temp);
		if (perm == null) {
			SysPermission sysPermission = initMenuPermission(0L, "系统管理", null, "01", "fa fa-cogs");
			boolean flag = sysPermission.insert();
			if (flag == true) {
				Long pid = sysPermissionMapper.selectOne(temp).getId();
				SysPermission userPermission = initMenuPermission(pid, "用户管理", "/system/user/list/1.html", "0101", "fa-user-circle-o");
				userPermission.insert();
				SysPermission uPermission = sysPermissionMapper.selectById(userPermission.getId());
				initFunctionPermission(uPermission.getId(), "查看用户列表", "010101", 1).insert();
				initFunctionPermission(uPermission.getId(), "新增用户", "010102", 2).insert();
				initFunctionPermission(uPermission.getId(), "删除用户", "010103", 3).insert();
				initFunctionPermission(uPermission.getId(), "编辑用户", "010104", 4).insert();

				SysPermission rolePermission = initMenuPermission(pid, "角色管理", "/system/role/list/1.html", "0102", "fa-users");
				rolePermission.insert();
				SysPermission rPermission = sysPermissionMapper.selectById(rolePermission.getId());
				initFunctionPermission(rPermission.getId(), "查看角色列表", "010201", 1).insert();
				initFunctionPermission(rPermission.getId(), "新增角色", "010202", 2).insert();
				initFunctionPermission(rPermission.getId(), "删除角色", "010203", 3).insert();
				initFunctionPermission(rPermission.getId(), "编辑角色", "010204", 4).insert();
				initFunctionPermission(rPermission.getId(), "角色授权", "010205", 5).insert();
				initFunctionPermission(rPermission.getId(), "批量删除角色", "010206", 6).insert();

				SysPermission permPermission = initMenuPermission(pid, "权限管理", "/system/perm/list/1.html", "0103", "fa-list");
				permPermission.insert();
				SysPermission pPermission = sysPermissionMapper.selectById(permPermission.getId());
				initFunctionPermission(pPermission.getId(), "查看权限列表", "010301", 1).insert();
				initFunctionPermission(pPermission.getId(), "新增权限", "010302", 2).insert();
				initFunctionPermission(pPermission.getId(), "删除权限", "010303", 3).insert();
				initFunctionPermission(pPermission.getId(), "编辑权限", "010304", 4).insert();

				SysPermission logPermission = initMenuPermission(pid, "日志管理", "/system/log/list/1.html", "0104", "fa-info-circle");
				logPermission.insert();
				SysPermission lPermission = sysPermissionMapper.selectById(logPermission.getId());
				initFunctionPermission(lPermission.getId(), "查看日志列表", "010401", 1).insert();
				initFunctionPermission(lPermission.getId(), "删除日志", "010402", 2).insert();

				SysPermission settingPermission = initMenuPermission(pid, "系统配置", "/system/setting/page.html", "0105", " fa-cog");
				settingPermission.insert();
				SysPermission sPermission = sysPermissionMapper.selectById(settingPermission.getId());
				initFunctionPermission(sPermission.getId(), "查看系统配置", "010501", 1).insert();
				initFunctionPermission(sPermission.getId(), "修改系统配置", "010502", 2).insert();

				SysPermission deptPermission = initMenuPermission(pid, "部门管理", "/system/dept/list/1.html", "0106", "fa-list");
				deptPermission.insert();
				SysPermission dPermission = sysPermissionMapper.selectById(deptPermission.getId());
				initFunctionPermission(dPermission.getId(), "查看部门列表", "010601", 1).insert();
				initFunctionPermission(dPermission.getId(), "新增部门", "010602", 2).insert();
				initFunctionPermission(dPermission.getId(), "删除部门", "010603", 3).insert();
				initFunctionPermission(dPermission.getId(), "编辑部门", "010604", 4).insert();


			}
		}
		SysPermission temp2 = new SysPermission();
		temp2.setPermName("性能管理");
		SysPermission perm2 = sysPermissionMapper.selectOne(temp2);
		if (perm2 == null) {
			SysPermission naPermission = initMenuPermission(0L, "性能管理", null, "01", "fa fa-cogs");
			naPermission.insert();
			Long pid2 = naPermission.getId();

			SysPermission prodClassPermission = initMenuPermission(pid2, "项目类别列表", "/pet/productclass/list", "0107", "fa-list");
			prodClassPermission.insert();
			SysPermission prodPermission = initMenuPermission(pid2, "项目列表", "/pet/product/list", "0107", "fa-list");
			prodPermission.insert();
			SysPermission petShowPermission = initMenuPermission(pid2, "性能数据列表", "/pet/showpet/list", "0107", "fa-list");
			petShowPermission.insert();
			SysPermission prClPermission = sysPermissionMapper.selectById(prodClassPermission.getId());
			initFunctionPermission(prClPermission.getId(), "查看项目类别列表", "010701", 1).insert();
			initFunctionPermission(prClPermission.getId(), "新增项目类别", "010702", 2).insert();
			initFunctionPermission(prClPermission.getId(), "删除项目类别", "010703", 3).insert();
			initFunctionPermission(prClPermission.getId(), "编辑项目类别", "010704", 4).insert();

			SysPermission prPermission = sysPermissionMapper.selectById(prodPermission.getId());
			initFunctionPermission(prPermission.getId(), "查看项目列表", "010701", 1).insert();
			initFunctionPermission(prPermission.getId(), "新增项目", "010702", 2).insert();
			initFunctionPermission(prPermission.getId(), "删除项目", "010703", 3).insert();
			initFunctionPermission(prPermission.getId(), "编辑项目", "010704", 4).insert();
		}
	}

	private SysPermission initMenuPermission(Long pid, String permName, String url, String permCode, String icon) {
		SysPermission sysPermission = new SysPermission();
		sysPermission.setPermName(permName);
		sysPermission.setPid(pid);
		sysPermission.setPermType(0);
		sysPermission.setUrl(url);
		sysPermission.setPermCode(permCode);
		sysPermission.setIcon(icon);
		sysPermission.setGmtCreate(new Date());
		return sysPermission;
	}

	private SysPermission initFunctionPermission(Long pid, String permName, String permCode, int sort) {
		SysPermission sysPermission = new SysPermission();
		sysPermission.setPermName(permName);
		sysPermission.setPid(pid);
		sysPermission.setPermType(1);
		sysPermission.setPermCode(permCode);
		sysPermission.setSort(sort);
		sysPermission.setGmtCreate(new Date());
		return sysPermission;
	}

	private void initRolePermission() {
		SysRole adminRole = new SysRole();
		adminRole.setRoleName("系统管理员");
		adminRole = sysRoleMapper.selectOne(adminRole);
		if (adminRole != null) {
			Long roleId = adminRole.getId();

			Wrapper<SysRolePermission> rpWrapper = new EntityWrapper<>();
			rpWrapper.eq("role_id", roleId);
			List<SysRolePermission> temp = sysRolePermissionMapper.selectList(rpWrapper);

			List<SysPermission> permissions = sysPermissionMapper.selectList(null);
			if (!permissions.isEmpty() && temp.isEmpty()) {
				for (SysPermission permission : permissions) {
					SysRolePermission rPermission = new SysRolePermission();
					rPermission.setPermId(permission.getId());
					rPermission.setRoleId(roleId);
					rPermission.insert();
				}
			}
		}
	}
}
