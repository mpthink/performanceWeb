package com.dell.petshow.vtas.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.JobRuntime;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
public interface JobRuntimeMapper extends BaseMapper<JobRuntime> {

	List<String> selectDistinctArrayList();

	List<Map<String, Object>> selectAllWithProgramName();

	List<Map<String, Object>> selectLatestJobsWithProgramName();

	String selectLatestVersionByArray(@Param("arrayName") String arrayName);

	List<String> selectDistinctArrayListByProgram(@Param("bigVersion") String bigVersion);

	List<String> selectVersionByProgramAndArray(@Param("bigVersion") String bigVersion, @Param("arrayName") String arrayName);

	List<String> selectRunHourBySmallVersionAndArray(@Param("smallVersion") String smallVersion, @Param("arrayName") String arrayName);

	Integer getMaxRowsOfRunHourByProgramAndArray(@Param("bigVersion") String bigVersion, @Param("arrayName") String arrayName);

	JobRuntime selectCurrentRunHourBySmallVersionAndArray(@Param("arrayName") String arrayName, @Param("smallVersion") String smallVersion);
}
