package com.nokia.netactplus.db.init;

import java.util.Date;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nokia.netactplus.system.entity.SysRole;
import com.nokia.netactplus.system.entity.SysUser;
import com.nokia.netactplus.system.entity.SysUserRole;
import com.nokia.netactplus.system.mapper.SysRoleMapper;
import com.nokia.netactplus.system.mapper.SysUserMapper;
import com.nokia.netactplus.system.mapper.SysUserRoleMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
@FixMethodOrder(MethodSorters.DEFAULT)
public class UserRoleDataInitial {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;


	@Test
	public void initFirstUser() {
		String userName = "paul.ma@nokia.com";
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


	@Test
	public void initUserRole() {
		String userName = "paul.ma@nokia.com";
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

}
