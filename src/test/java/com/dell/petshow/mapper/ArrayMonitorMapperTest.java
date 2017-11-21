package com.dell.petshow.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.vtas.mapper.ArrayMonitorMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class ArrayMonitorMapperTest {

	@Autowired
	private ArrayMonitorMapper arrayMonitorMapper;

	@Test
	public void dummy() {}


}
