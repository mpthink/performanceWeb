package com.nokia.netactplus.db.init;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nokia.netactplus.system.entity.SysRole;
import com.nokia.netactplus.system.mapper.SysRoleMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class RoleDataInitial {

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Test
	public void initRoleData() {
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

}
