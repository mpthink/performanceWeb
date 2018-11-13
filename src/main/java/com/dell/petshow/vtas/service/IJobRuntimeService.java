package com.dell.petshow.vtas.service;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.vtas.entity.JobRuntime;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
public interface IJobRuntimeService extends IService<JobRuntime> {

	Map<String, Object> exeutionStatusForWeb(String beginTime, String endTime);

	/**
	 * Get one month data of execution status and generate a file
	 * @return string generated file name
	 */
	Map<String, Object> exeutionStatusForMail();

	List<String> selectDistinctArrayList();

	List<Map<String, Object>> selectAllWithProgramName();

	List<Map<String, Object>> selectLatestJobsWithProgramName();

	Map<String, Object> selectMaxHourByProgramAndArray(String bigVersion, String arrayName);

	List<String> selectDistinctArrayListByProgram(String bigVersion);

	List<String> selectVersionByProgramAndArray(String bigVersion, String arrayName);

	List<String> selectRunHourBySmallVersionAndArray(String smallVersion, String arrayName);

	Integer getMaxRowsOfRunHourByProgramAndArray(String bigVersion, String arrayName);

}
