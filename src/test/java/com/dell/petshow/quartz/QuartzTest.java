package com.dell.petshow.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzTest {

	public static void main(String[] args) throws SchedulerException, InterruptedException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail jobDetail = newJob(Hello.class).usingJobData("dataKey", "dataValue").withIdentity("job1", "group1").build();

		Trigger trigger = newTrigger().withIdentity("job1", "group1").startNow()
			.withSchedule(simpleSchedule().withIntervalInSeconds(1).withRepeatCount(5)).build();
		scheduler.scheduleJob(jobDetail, trigger);
		System.err.println(scheduler.isShutdown());
		if (!scheduler.isShutdown()) {
			scheduler.start();
		}
		Thread.sleep(4000);
		Trigger trigger2 = newTrigger().withIdentity("job1", "group1").startNow()
			.withSchedule(simpleSchedule().withIntervalInSeconds(2).withRepeatCount(3)).build();

		TriggerKey triggerKey = TriggerKey.triggerKey("job1", "group1");
		scheduler.rescheduleJob(triggerKey, trigger2);
		System.out.println(scheduler.getJobGroupNames());
		System.err.println(scheduler.getTriggerGroupNames());
		System.out.println("------- 等待60秒 ... -------------");
		try {
			Thread.sleep(60L * 1000L);
		} catch (Exception e) {
		}

		scheduler.shutdown(true);
	}

}
