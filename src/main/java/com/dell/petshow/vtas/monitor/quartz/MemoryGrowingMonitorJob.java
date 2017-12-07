package com.dell.petshow.vtas.monitor.quartz;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dell.petshow.vtas.entity.ArrayInfo;
import com.dell.petshow.vtas.mapper.ArrayInfoMapper;
import com.dell.petshow.vtas.monitor.service.Strategy;
import com.dell.petshow.vtas.monitor.service.impl.MemoryAnalyze;

public class MemoryGrowingMonitorJob implements BeanFactoryAware {

	@Autowired
	private ArrayInfoMapper arrayInfoMapper;

	private BeanFactory factory;

	@Autowired
	@Qualifier("MemoryGrowthStrategy")
	private Strategy strategy;

	public void doAnalysisJob() {
		ThreadPoolExecutor threadPool =
			new ThreadPoolExecutor(2, 4, 6, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10), new ThreadPoolExecutor.CallerRunsPolicy());
		List<ArrayInfo> arrayInfos = arrayInfoMapper.selectList(null);
		for (ArrayInfo arrayInfo : arrayInfos) {
			threadPool.execute(factory.getBean(MemoryAnalyze.class, arrayInfo.getArrayName(), strategy));
		}
		threadPool.shutdown();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.factory = beanFactory;
	}
}
