package com.dell.petshow.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.entity.SysPermission;
import com.dell.petshow.system.mapper.SysPermissionMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class SysPermissionMapperTest {

	@Autowired
	private SysPermissionMapper sysPermissionMapper;

	@Test
	public void dummy() {}

	//@Test
	public void testSelect() {

		List<SysPermission> permissions = sysPermissionMapper.selectMenuByuserIdAndPid(855033115163279360L, 0L);

		System.err.println("test...." + permissions.toString());
	}
}
