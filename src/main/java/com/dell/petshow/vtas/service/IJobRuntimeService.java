package com.dell.petshow.vtas.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.vtas.entity.JobRuntime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
public interface IJobRuntimeService extends IService<JobRuntime> {

	List<String> selectDistinctArrayList();

	List<Map<String, Object>> selectAllWithProgramName();

	List<Map<String, Object>> selectLatestJobsWithProgramName();

	List<String> selectDistinctArrayListByProgram(String bigVersion);

	List<String> selectVersionByProgramAndArray(String bigVersion, String arrayName);

	List<String> selectRunHourBySmallVersionAndArray(String smallVersion, String arrayName);

	Integer getMaxRowsOfRunHourByProgramAndArray(String bigVersion, String arrayName);

}
