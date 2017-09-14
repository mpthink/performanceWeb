package com.nokia.netactplus.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class ImportLabData {

	@Test
	public void insertCloudLabData() throws InterruptedException {
		ThreadPoolExecutor threadPoolExecutor =
			new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(30), new ThreadPoolExecutor.CallerRunsPolicy());

		for (int i = 1350; i < 1360; i++) {
			threadPoolExecutor.execute(new CloudLabSpider(i));
		}

		Thread.sleep(20000);

	}
}
