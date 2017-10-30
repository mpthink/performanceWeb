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
		createSettings("systemName", "System Name", "VTAS", 0);
		createSettings("systemSubName", "System Short Name", "V+", 1);
		createSettings("bottomCopyright", "License", "Copyright © 2017 Dell. All rights reserved.", 2);
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
		adminRole.setRoleName("Administrator");
		if (sysRoleMapper.selectOne(adminRole) == null) {
			adminRole.insert();
		}

		SysRole generalRole = new SysRole();
		generalRole.setRoleName("Member");
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
			role.setRoleName("Administrator");
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
		temp.setPermName("System Management");
		SysPermission perm = sysPermissionMapper.selectOne(temp);
		if (perm == null) {
			SysPermission sysPermission = initMenuPermission(0L, "System Management", null, "01", "fa fa-cogs");
			boolean flag = sysPermission.insert();
			if (flag == true) {
				Long pid = sysPermissionMapper.selectOne(temp).getId();
				SysPermission userPermission = initMenuPermission(pid, "Member", "/system/user/list/1.html", "0101", "fa-user-circle-o");
				userPermission.insert();
				SysPermission uPermission = sysPermissionMapper.selectById(userPermission.getId());
				initFunctionPermission(uPermission.getId(), "User List", "010101", 1).insert();
				initFunctionPermission(uPermission.getId(), "Add User", "010102", 2).insert();
				initFunctionPermission(uPermission.getId(), "Del User", "010103", 3).insert();
				initFunctionPermission(uPermission.getId(), "Edit User", "010104", 4).insert();

				SysPermission rolePermission = initMenuPermission(pid, "Role Management", "/system/role/list/1.html", "0102", "fa-users");
				rolePermission.insert();
				SysPermission rPermission = sysPermissionMapper.selectById(rolePermission.getId());
				initFunctionPermission(rPermission.getId(), "Role List", "010201", 1).insert();
				initFunctionPermission(rPermission.getId(), "Add Role", "010202", 2).insert();
				initFunctionPermission(rPermission.getId(), "Del Role", "010203", 3).insert();
				initFunctionPermission(rPermission.getId(), "Edit Role", "010204", 4).insert();
				initFunctionPermission(rPermission.getId(), "Role Grant", "010205", 5).insert();
				initFunctionPermission(rPermission.getId(), "Batch Del", "010206", 6).insert();

				SysPermission permPermission = initMenuPermission(pid, "Permission Management", "/system/perm/list/1.html", "0103", "fa-list");
				permPermission.insert();
				SysPermission pPermission = sysPermissionMapper.selectById(permPermission.getId());
				initFunctionPermission(pPermission.getId(), "Perm List", "010301", 1).insert();
				initFunctionPermission(pPermission.getId(), "Add Perm", "010302", 2).insert();
				initFunctionPermission(pPermission.getId(), "Del Perm", "010303", 3).insert();
				initFunctionPermission(pPermission.getId(), "Edit Perm", "010304", 4).insert();

				SysPermission logPermission = initMenuPermission(pid, "Log Management", "/system/log/list/1.html", "0104", "fa-info-circle");
				logPermission.insert();
				SysPermission lPermission = sysPermissionMapper.selectById(logPermission.getId());
				initFunctionPermission(lPermission.getId(), "Log List", "010401", 1).insert();
				initFunctionPermission(lPermission.getId(), "Del Log", "010402", 2).insert();

				SysPermission settingPermission = initMenuPermission(pid, "System Config", "/system/setting/page.html", "0105", " fa-cog");
				settingPermission.insert();
				SysPermission sPermission = sysPermissionMapper.selectById(settingPermission.getId());
				initFunctionPermission(sPermission.getId(), "Config List", "010501", 1).insert();
				initFunctionPermission(sPermission.getId(), "Edit Config", "010502", 2).insert();

				SysPermission deptPermission = initMenuPermission(pid, "Department management", "/system/dept/list/1.html", "0106", "fa-list");
				deptPermission.insert();
				SysPermission dPermission = sysPermissionMapper.selectById(deptPermission.getId());
				initFunctionPermission(dPermission.getId(), "Department List", "010601", 1).insert();
				initFunctionPermission(dPermission.getId(), "Add Department", "010602", 2).insert();
				initFunctionPermission(dPermission.getId(), "Del Department", "010603", 3).insert();
				initFunctionPermission(dPermission.getId(), "Edit Department", "010604", 4).insert();


			}
		}
		SysPermission temp2 = new SysPermission();
		temp2.setPermName("Execution Status");
		SysPermission perm2 = sysPermissionMapper.selectOne(temp2);
		if (perm2 == null) {
			SysPermission naPermission = initMenuPermission(0L, "Execution Status", null, "02", "fa fa-cogs");
			naPermission.insert();
			Long pid2 = naPermission.getId();

			SysPermission prodClassPermission = initMenuPermission(pid2, "Jenkins Job Execuation", "/vtas/jobRuntime/listJobs", "0201", "fa-list");
			prodClassPermission.insert();
			SysPermission prodPermission = initMenuPermission(pid2, "Runtime VS AR", "/vtas/jobRuntime/vsAR", "0202", "fa-list");
			prodPermission.insert();
			SysPermission petShowPermission = initMenuPermission(pid2, "Runtime VS Build", "/vtas/jobRuntime/vsBuild", "0202", "fa-list");
			petShowPermission.insert();
			SysPermission prClPermission = sysPermissionMapper.selectById(prodClassPermission.getId());
			initFunctionPermission(prClPermission.getId(), "Job List", "020101", 1).insert();
			SysPermission prPermission = sysPermissionMapper.selectById(prodPermission.getId());
			initFunctionPermission(prPermission.getId(), "View RunHour", "020201", 1).insert();
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
		adminRole.setRoleName("Administrator");
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
