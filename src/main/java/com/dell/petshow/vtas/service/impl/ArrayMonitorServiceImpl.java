package com.dell.petshow.vtas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.ArrayMonitor;
import com.dell.petshow.vtas.mapper.ArrayMonitorMapper;
import com.dell.petshow.vtas.service.IArrayMonitorService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-11-02
 */
@Service
public class ArrayMonitorServiceImpl extends ServiceImpl<ArrayMonitorMapper, ArrayMonitor> implements IArrayMonitorService {

	@Autowired
	private ArrayMonitorMapper arrayMonitorMapper;

	@Override
	@Cacheable(value = "commonCache")
	public List<Map<String, String>> selectDistinctArrayList(String tableName) {
		if (tableName == "" || tableName == null) {
			return null;
		}
		List<String> arrayList = arrayMonitorMapper.selectDistinctArrayList(tableName);
		List<Map<String, String>> listMap = new ArrayList<>();
		for (String arrayName : arrayList) {
			Map<String, String> map = new HashMap<>();
			map.put("id", arrayName);
			map.put("text", arrayName);
			listMap.add(map);
		}
		return listMap;
	}

	@Override
	@Cacheable(value = "commonCache")
	public List<Map<String, String>> selectDistinctVersionByArray(String tableName, String arrayName) {
		if (tableName == "" || tableName == null) {
			return null;
		}
		List<String> versionList = arrayMonitorMapper.selectDistinctVersionByArray(tableName, arrayName);
		List<Map<String, String>> listMap = new ArrayList<>();
		for (String versionName : versionList) {
			Map<String, String> map = new HashMap<>();
			map.put("id", versionName);
			map.put("text", versionName);
			listMap.add(map);
		}
		return listMap;
	}

	@Override
	@Cacheable(value = "arrayMonitorMemoryCache")
	public List<Map<String, Object>> selectMemoryListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName,
		String beginTime, String endTime) {
		return arrayMonitorMapper.selectMemoryListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime);
	}

	@Override
	@Cacheable(value = "arrayMonitorCPUCache")
	public List<Map<String, Object>> selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName,
		String smallVersion, String beginTime, String endTime) {
		return arrayMonitorMapper.selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, smallVersion, beginTime,
			endTime);
	}

}
