package com.nokia.netactplus.common.quartz.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob3 implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.err.println("Job3 testing, added via quartz manager~");
	}

}
