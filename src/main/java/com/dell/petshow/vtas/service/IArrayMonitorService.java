package com.dell.petshow.vtas.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.vtas.entity.ArrayMonitor;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-11-02
 */
public interface IArrayMonitorService extends IService<ArrayMonitor> {

	List<Map<String, String>> selectDistinctArrayList(String tableName);

	List<Map<String, String>> selectDistinctVersionByArray(String tableName, String arrayName);

	List<Map<String, Object>> selectMemoryListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName, String beginTime,
		String endTime);

	List<Map<String, Object>> selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName, String beginTime,
		String endTime);

	List<Map<String, Object>> selectDiskListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName, String beginTime,
		String endTime);

	List<Map<String, Object>> selectIOPSListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName, String beginTime,
		String endTime);

	Map<String, Object> getArrayUptimeForDashBoard();

	Map<String, Object> getArrayCPUMemoryIOForDashBoard();

	List<Map<String, Object>> selectBandwidthListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName, String beginTime,
		String endTime);
}
