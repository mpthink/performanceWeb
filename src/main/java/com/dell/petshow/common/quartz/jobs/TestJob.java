package com.dell.petshow.common.quartz.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution // 不允许并发执行
public class TestJob extends QuartzJobBean {

	private final static Logger LOGGER = LoggerFactory.getLogger(TestJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		System.err.println("Job test1 start");
		LOGGER.debug("Job test1 start");
	}


}
