package com.dell.petshow.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.entity.vo.PetProductClassVO;
import com.dell.petshow.system.mapper.PetProductClassMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class PetProductClassMapperTest {

	@Autowired
	private PetProductClassMapper petProductClassMapper;

	@Test
	public void testSelectList() {

		List<PetProductClassVO> test = petProductClassMapper.selectWithPclassName();
		System.err.println(test);
	}

}
