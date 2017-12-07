package com.dell.petshow.quartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Hello implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
		System.out.println("Job data map: " + jobDataMap.getString("dataKey"));
		System.out.println("This is my first quartz job!");
	}

}
