package com.nokia.netactplus.common;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestSpider {

	public static void main(String[] args) {
		ThreadPoolExecutor executorService =
			new ThreadPoolExecutor(6, 12, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(3), new ThreadPoolExecutor.CallerRunsPolicy());
		//ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
		for (int i = 0; i < 2225; i++) {
			executorService.execute(new CloudLabSpider(i));
		}
		executorService.shutdown();
		if (executorService.isTerminated()) {
			System.out.println(CloudLabSpider.notFoundList.toString());
			System.out.println(CloudLabSpider.invalidList.toString());
			System.out.println(CloudLabSpider.otherList.toString());
			System.out.println(CloudLabSpider.validList.toString());
		}
	}

}
