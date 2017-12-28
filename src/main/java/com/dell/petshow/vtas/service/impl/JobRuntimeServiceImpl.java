package com.dell.petshow.vtas.service.impl;

import java.util.List;
import java.util.Map;

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
	public List<String> selectDistinctArrayList() {
		return jobRuntimeMapper.selectDistinctArrayList();
	}

	@Override
	@Cacheable(value = "commonCache")
	public List<String> selectDistinctArrayListByProgram(String bigVersion) {
		return jobRuntimeMapper.selectDistinctArrayListByProgram(bigVersion);
	}

	@Override
	@Cacheable(value = "commonCache")
	public List<String> selectVersionByProgramAndArray(String bigVersion, String arrayName) {
		return jobRuntimeMapper.selectVersionByProgramAndArray(bigVersion, arrayName);
	}

	@Override
	public List<String> selectRunHourBySmallVersionAndArray(String smallVersion, String arrayName) {
		return jobRuntimeMapper.selectRunHourBySmallVersionAndArray(smallVersion, arrayName);
	}

	@Override
	public Integer getMaxRowsOfRunHourByProgramAndArray(String bigVersion, String arrayName) {
		return jobRuntimeMapper.getMaxRowsOfRunHourByProgramAndArray(bigVersion, arrayName);
	}

	@Cacheable(value = "commonCache", key = "methodName")
	@Override
	public List<Map<String, Object>> selectAllWithProgramName() {
		return jobRuntimeMapper.selectAllWithProgramName();
	}

	@Override
	public List<Map<String, Object>> selectLatestJobsWithProgramName() {
		return jobRuntimeMapper.selectLatestJobsWithProgramName();
	}

	@Override
	public Map<String, Object> selectMaxHourByProgramAndArray(String bigVersion, String arrayName) {
		return jobRuntimeMapper.selectMaxHourByProgramAndArray(bigVersion, arrayName);
	}

}
