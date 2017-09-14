package com.nokia.netactplus.quartz;

import java.text.ParseException;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nokia.netactplus.common.quartz.QuartzManager;

public class SpringQuartzTest {

	public static void main(String[] args) throws SchedulerException, ParseException {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:spring/applicationContext-dao.xml",
			"classpath:spring/applicationContext-quartz.xml", "classpath:spring/applicationContext-service.xml"});
		QuartzManager quartzManager = (QuartzManager) context.getBean("quartzManager");
		System.err.println(quartzManager.isJobAdded("job1", "group1"));
		quartzManager.removeJob("job1", "group1", "trigger1", "DEFAULT");
	}



}
