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
	public void initData() {
		initSystemMenus();
	}

	private void initSystemMenus() {
		SysPermission temp = new SysPermission();
		temp.setPermName("Execution Status");
		SysPermission perm = sysPermissionMapper.selectOne(temp);
		if (perm == null) {
			SysPermission sysPermission = initMenuPermission(0L, "Execution Status", null, "02", "fa fa-cogs");
			boolean flag = sysPermission.insert();
			if (flag == true) {
				Long pid = sysPermissionMapper.selectOne(temp).getId();
				SysPermission jenkinsPermission =
					initMenuPermission(pid, "Jenkins Job Execution", "/vtas/jenkinsJob/list/1.html", "0201", "fa-user-circle-o");
				jenkinsPermission.insert();
				SysPermission jPermission = sysPermissionMapper.selectById(jenkinsPermission.getId());
				initFunctionPermission(jPermission.getId(), "Check Jenkins Job", "020101", 1).insert();

				SysPermission rtARPermission = initMenuPermission(pid, "RunTime VS AR", "/vtas/runtime/build.html", "0202", "fa-users");
				rtARPermission.insert();
				SysPermission arPermission = sysPermissionMapper.selectById(rtARPermission.getId());
				initFunctionPermission(arPermission.getId(), "Check runtime build", "020201", 1).insert();

				SysPermission rtBuildPermission = initMenuPermission(pid, "RunTime VS Build", "/vtas/runtime/build.html", "0202", "fa-users");
				rtBuildPermission.insert();
				SysPermission bPermission = sysPermissionMapper.selectById(rtBuildPermission.getId());
				initFunctionPermission(bPermission.getId(), "Check runtime build", "020201", 1).insert();

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
