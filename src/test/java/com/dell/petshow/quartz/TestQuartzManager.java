package com.dell.petshow.quartz;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.common.quartz.QuartzManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-quartz.xml"})
public class TestQuartzManager {

	@Autowired
	private QuartzManager quartzManager;


	@Test
	public void testQuartzManager() throws SchedulerException, ParseException {
		System.err.println(quartzManager.isJobAdded("job1", "group1"));

	}
}
