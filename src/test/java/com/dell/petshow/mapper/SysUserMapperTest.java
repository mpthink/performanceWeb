package com.dell.petshow.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.entity.SysUser;
import com.dell.petshow.system.mapper.SysUserMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class SysUserMapperTest {

	@Autowired
	private SysUserMapper usermapper;

	@Test
	public void testSelectList() {

		List<SysUser> users = usermapper.selectList(null);
		System.err.println(users);
	}

}
