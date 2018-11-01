package com.dell.petshow.service;

import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.service.IJobRuntimeService;
import com.dell.petshow.vtas.service.IProgramMapService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-ehcache.xml",
	"classpath:spring/applicationContext-service.xml"})
public class VTASServices {

	@Autowired
	private IProgramMapService programMapService;

	@Autowired
	private IJobRuntimeService jobRuntimeService;

	@Test
	public void dummy() {}

	//@Test
	public void testProgramMap() {
		List<ProgramMap> test = programMapService.selectAll();
		System.err.println(test.toString());
	}

	@Test
	public void testJobs() {
		String beginTime = "2018-09-10 10:00:00";
		String endTime = "2018-10-23 00:50:00";
		System.err.println(jobRuntimeService.exeutionStatusForWeb(beginTime, endTime));
	}

}
