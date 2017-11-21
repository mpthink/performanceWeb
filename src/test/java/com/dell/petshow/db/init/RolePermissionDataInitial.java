package com.dell.petshow.db.init;

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
import com.dell.petshow.system.mapper.SysPermissionMapper;
import com.dell.petshow.system.mapper.SysRoleMapper;
import com.dell.petshow.system.mapper.SysRolePermissionMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class RolePermissionDataInitial {

	@Autowired
	private SysRolePermissionMapper sysRolePermissionMapper;

	@Autowired
	private SysRoleMapper sysRoleMapper;

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Test
	public void dummy() {}

	//@Test
	public void initRolePermission() {
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
