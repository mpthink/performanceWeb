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
public class InitialDataForVTAS {
	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Test
	public void dummy() {}

	@Test
	public void initData() {
		initSystemMenus();
	}

	private void initSystemMenus() {
		SysPermission temp = new SysPermission();
		temp.setPermName("Array Monitor");
		SysPermission perm = sysPermissionMapper.selectOne(temp);
		if (perm == null) {
			SysPermission sysPermission = initMenuPermission(0L, "Array Monitor", null, "03", "fa fa-cogs");
			boolean flag = sysPermission.insert();
			if (flag == true) {
				Long pid = sysPermissionMapper.selectOne(temp).getId();
				SysPermission jenkinsPermission =
					initMenuPermission(pid, "Memory", "/vtas/arraymonitor/memory", "0301", "fa-user-circle-o");
				jenkinsPermission.insert();

				SysPermission rtARPermission = initMenuPermission(pid, "CPU", "/vtas/arraymonitor/cpu", "0402", "fa-users");
				rtARPermission.insert();

				SysPermission rtBuildPermission = initMenuPermission(pid, "Disk", "/vtas/arraymonitor/disk", "0303", "fa-users");
				rtBuildPermission.insert();

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
