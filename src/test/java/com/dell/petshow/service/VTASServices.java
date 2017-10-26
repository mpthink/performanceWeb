package com.dell.petshow.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.service.IProgramMapService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-ehcache.xml",
	"classpath:spring/applicationContext-service.xml"})
public class VTASServices {

	@Autowired
	private IProgramMapService programMapService;

	@Test
	public void testProgramMap() {
		List<ProgramMap> test = programMapService.selectAll();
		System.err.println(test.toString());
	}

}
