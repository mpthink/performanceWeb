package com.dell.petshow.vtas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.JobRuntime;
import com.dell.petshow.vtas.mapper.JobRuntimeMapper;
import com.dell.petshow.vtas.service.IJobRuntimeService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Service
public class JobRuntimeServiceImpl extends ServiceImpl<JobRuntimeMapper, JobRuntime> implements IJobRuntimeService {

	@Autowired
	private JobRuntimeMapper jobRuntimeMapper;

	@Override
	@Cacheable(value = "commonCache")
	public List<String> selectDistinctArrayList() {
		return jobRuntimeMapper.selectDistinctArrayList();
	}

}