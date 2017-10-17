package com.dell.petshow.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.service.IPetCpuHourService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-ehcache.xml",
	"classpath:spring/applicationContext-service.xml"})
public class TestCpuHourServices {

	@Autowired
	private IPetCpuHourService petCpuHourService;

	@Test
	public void testCronJob() {
		petCpuHourService.cronJobForCpuHourData();
	}

}
