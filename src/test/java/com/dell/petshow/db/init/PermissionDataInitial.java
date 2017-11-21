package com.dell.petshow.db.init;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.entity.SysPermission;
import com.dell.petshow.system.mapper.SysPermissionMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class PermissionDataInitial {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Test
	public void dummy() {}

	@Test
	public void initSystemMenus() {
		SysPermission temp = new SysPermission();
		temp.setPermName("系统管理");
		SysPermission perm = sysPermissionMapper.selectOne(temp);
		if (perm == null) {
			SysPermission sysPermission = initMenuPermission(0L, "系统管理", null, "01", "fa fa-cogs");
			boolean flag = sysPermission.insert();
			if (flag == true) {
				Long pid = sysPermissionMapper.selectOne(temp).getId();
				SysPermission userPermission = initMenuPermission(pid, "用户管理", "/system/user/list.html", "0101", "fa-user-circle-o");
				userPermission.insert();
				SysPermission uPermission = sysPermissionMapper.selectById(userPermission.getId());
				initFunctionPermission(uPermission.getId(), "查看用户列表", "010101", 1).insert();
				initFunctionPermission(uPermission.getId(), "新增用户", "010102", 2).insert();
				initFunctionPermission(uPermission.getId(), "删除用户", "010103", 3).insert();
				initFunctionPermission(uPermission.getId(), "编辑用户", "010104", 4).insert();

				SysPermission rolePermission = initMenuPermission(pid, "角色管理", "/system/role/list.html", "0102", "fa-users");
				rolePermission.insert();
				SysPermission rPermission = sysPermissionMapper.selectById(rolePermission.getId());
				initFunctionPermission(rPermission.getId(), "查看角色列表", "010201", 1).insert();
				initFunctionPermission(rPermission.getId(), "新增角色", "010202", 2).insert();
				initFunctionPermission(rPermission.getId(), "删除角色", "010203", 3).insert();
				initFunctionPermission(rPermission.getId(), "编辑角色", "010204", 4).insert();
				initFunctionPermission(rPermission.getId(), "角色授权", "010205", 5).insert();
				initFunctionPermission(rPermission.getId(), "批量删除角色", "010206", 6).insert();

				SysPermission permPermission = initMenuPermission(pid, "权限管理", "/system/perm/list.html", "0103", "fa-list");
				permPermission.insert();
				SysPermission pPermission = sysPermissionMapper.selectById(permPermission.getId());
				initFunctionPermission(pPermission.getId(), "查看权限列表", "010301", 1).insert();
				initFunctionPermission(pPermission.getId(), "新增权限", "010302", 2).insert();
				initFunctionPermission(pPermission.getId(), "删除权限", "010303", 3).insert();
				initFunctionPermission(pPermission.getId(), "编辑权限", "010304", 4).insert();

				SysPermission logPermission = initMenuPermission(pid, "日志管理", "/system/log/list.html", "0104", "fa-info-circle");
				logPermission.insert();
				SysPermission lPermission = sysPermissionMapper.selectById(logPermission.getId());
				initFunctionPermission(lPermission.getId(), "查看日志列表", "010401", 1).insert();
				initFunctionPermission(lPermission.getId(), "删除日志", "010402", 2).insert();

				SysPermission settingPermission = initMenuPermission(pid, "系统配置", "/system/setting/config.html", "0105", " fa-cog");
				settingPermission.insert();
				SysPermission sPermission = sysPermissionMapper.selectById(settingPermission.getId());
				initFunctionPermission(sPermission.getId(), "查看系统配置", "010501", 1).insert();
				initFunctionPermission(sPermission.getId(), "修改系统配置", "010502", 2).insert();


			}
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

}
