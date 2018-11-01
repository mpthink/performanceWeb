package com.dell.petshow.vtas.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.ArrayMonitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-11-02
 */
@Repository
@Mapper
public interface ArrayMonitorMapper extends BaseMapper<ArrayMonitor> {

	List<String> selectDistinctArrayList(@Param("tableName") String tableName);

	List<String> selectDistinctVersionByArray(@Param("tableName") String tableName, @Param("arrayName") String arrayName);

	List<Map<String, Object>> selectMemoryListBasedOnTableNameWithArrayAndVersionAndTimeslot(@Param("tableName") String tableName,
		@Param("arrayName") String arrayName, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	List<Map<String, Object>> selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(@Param("tableName") String tableName,
		@Param("arrayName") String arrayName, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	List<Map<String, Object>> selectDiskListBasedOnTableNameWithArrayAndVersionAndTimeslot(@Param("tableName") String tableName,
		@Param("arrayName") String arrayName, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	List<Map<String, Object>> selectIOPSListBasedOnTableNameWithArrayAndVersionAndTimeslot(@Param("tableName") String tableName,
		@Param("arrayName") String arrayName, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	Map<String, Object> selectLatestOneForDashBoardByProgramAndArrayAndSPType(@Param("tableName") String tableName,
		@Param("arrayName") String arrayName, @Param("spType") String spType);

	List<Map<String, Object>> selectBandwidthListBasedOnTableNameWithArrayAndVersionAndTimeslot(@Param("tableName") String tableName,
		@Param("arrayName") String arrayName, @Param("beginTime") String beginTime, @Param("endTime") String endTime);
}
