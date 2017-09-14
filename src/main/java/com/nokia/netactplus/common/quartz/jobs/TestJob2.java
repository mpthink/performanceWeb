package com.nokia.netactplus.common.quartz.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJob2 {

	private final static Logger LOGGER = LoggerFactory.getLogger(TestJob2.class);

	public void doSomething() {
		System.err.println("Job Test2, just for test");
		LOGGER.debug("Job Test2, just for test");
	}

}
