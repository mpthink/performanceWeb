package com.dell.petshow.vtas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.ArrayInfo;
import com.dell.petshow.vtas.entity.ArrayMonitor;
import com.dell.petshow.vtas.entity.SpUptime;
import com.dell.petshow.vtas.mapper.ArrayInfoMapper;
import com.dell.petshow.vtas.mapper.ArrayMonitorMapper;
import com.dell.petshow.vtas.mapper.SpUptimeMapper;
import com.dell.petshow.vtas.service.IArrayMonitorService;
import com.dell.petshow.vtas.service.IProgramMapService;

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
	@Autowired
	private ArrayInfoMapper arrayInfoMapper;
	@Autowired
	private SpUptimeMapper spUptimeMapper;
	@Autowired
	private IProgramMapService programMapService;

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
	public List<Map<String, Object>> selectBandwidthListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName,
		String beginTime, String endTime) {
		return arrayMonitorMapper.selectBandwidthListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime);
	}

	@Override
	@Cacheable(value = "arrayMonitorCPUCache")
	public List<Map<String, Object>> selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName,
		String beginTime, String endTime) {
		return arrayMonitorMapper.selectCPUListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime);
	}

	@Override
	@Cacheable(value = "arrayMonitorDiskCache")
	public List<Map<String, Object>> selectDiskListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName,
		String beginTime, String endTime) {
		return arrayMonitorMapper.selectDiskListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime);
	}

	@Override
	@Cacheable(value = "arrayMonitorIOPSCache")
	public List<Map<String, Object>> selectIOPSListBasedOnTableNameWithArrayAndVersionAndTimeslot(String tableName, String arrayName,
		String beginTime, String endTime) {
		return arrayMonitorMapper.selectIOPSListBasedOnTableNameWithArrayAndVersionAndTimeslot(tableName, arrayName, beginTime, endTime);
	}

	@Cacheable(value = "commonCache", key = "methodName")
	@Override
	public Map<String, Object> getArrayUptimeForDashBoard() {
		List<String> arrayNameList = new ArrayList<>();
		List<Integer> spaUptimeList = new ArrayList<>();
		List<Integer> spbUptimeList = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		Wrapper<ArrayInfo> aWrapper = new EntityWrapper<>();
		aWrapper.ne("USAGE_TYPE", "cndu");
		List<ArrayInfo> arrayInfos = arrayInfoMapper.selectList(aWrapper);
		for (ArrayInfo arrayInfo : arrayInfos) {
			String arrayName = arrayInfo.getArrayName();
			SpUptime spaUptime = spUptimeMapper.selectLatestOneByArrayAndSPType(arrayName, "SPA");
			SpUptime spbUptime = spUptimeMapper.selectLatestOneByArrayAndSPType(arrayName, "SPB");
			arrayNameList.add(arrayName);
			if (spaUptime == null) {
				spaUptimeList.add(0);
			} else {
				spaUptimeList.add(spaUptime.getUptime());
			}

			if (spbUptime == null) {
				spbUptimeList.add(0);
			} else {
				spbUptimeList.add(spbUptime.getUptime());
			}
		}
		resultMap.put("arrayNameList", arrayNameList);
		resultMap.put("spaUptimeList", spaUptimeList);
		resultMap.put("spbUptimeList", spbUptimeList);
		return resultMap;
	}

	@Cacheable(value = "commonCache", key = "methodName")
	@Override
	public Map<String, Object> getArrayCPUMemoryIOForDashBoard() {
		List<String> arrayNameList = new ArrayList<>();
		List<Integer> spaCPUList = new ArrayList<>();
		List<Integer> spbCPUList = new ArrayList<>();
		List<Integer> spaMemUsedList = new ArrayList<>();
		List<Integer> spbMemUsedList = new ArrayList<>();
		List<Integer> spaIOReadList = new ArrayList<>();
		List<Integer> spaIOWriteList = new ArrayList<>();
		List<Integer> spbIOReadList = new ArrayList<>();
		List<Integer> spbIOWriteList = new ArrayList<>();
		Map<String, Object> resultMap = new HashMap<>();
		Wrapper<ArrayInfo> aWrapper = new EntityWrapper<>();
		aWrapper.ne("USAGE_TYPE", "cndu");
		List<ArrayInfo> arrayInfos = arrayInfoMapper.selectList(aWrapper);
		for (ArrayInfo arrayInfo : arrayInfos) {
			String arrayName = arrayInfo.getArrayName();
			SpUptime spaUptime = spUptimeMapper.selectLatestOneByArrayAndSPType(arrayName, "SPA");
			String smallVersion = spaUptime.getVersion();
			String tableName = getTableName(smallVersion);
			Map<String, Object> spaData = arrayMonitorMapper.selectLatestOneForDashBoardByProgramAndArrayAndSPType(tableName, arrayName, "SPA");
			Map<String, Object> spbData = arrayMonitorMapper.selectLatestOneForDashBoardByProgramAndArrayAndSPType(tableName, arrayName, "SPB");
			arrayNameList.add(arrayName);
			if (spaData == null) {
				spaCPUList.add(0);
				spaMemUsedList.add(0);
				spaIOReadList.add(0);
				spaIOWriteList.add(0);
			} else {
				spaCPUList.add((Integer) spaData.get("CPU_FILT"));
				spaMemUsedList.add((Integer) spaData.get("MEM_USED"));
				spaIOReadList.add((Integer) spaData.get("IO_READ"));
				spaIOWriteList.add((Integer) spaData.get("IO_WRITE"));
			}
			if (spbData == null) {
				spbCPUList.add(0);
				spbMemUsedList.add(0);
				spbIOReadList.add(0);
				spbIOWriteList.add(0);
			} else {
				spbCPUList.add((Integer) spbData.get("CPU_FILT"));
				spbMemUsedList.add((Integer) spbData.get("MEM_USED"));
				spbIOReadList.add((Integer) spbData.get("IO_READ"));
				spbIOWriteList.add((Integer) spbData.get("IO_WRITE"));
			}
		}
		resultMap.put("arrayNameList", arrayNameList);
		resultMap.put("spaCPUList", spaCPUList);
		resultMap.put("spbCPUList", spbCPUList);
		resultMap.put("spaMemUsedList", spaMemUsedList);
		resultMap.put("spbMemUsedList", spbMemUsedList);
		resultMap.put("spaIOReadList", spaIOReadList);
		resultMap.put("spaIOWriteList", spaIOWriteList);
		resultMap.put("spbIOReadList", spbIOReadList);
		resultMap.put("spbIOWriteList", spbIOWriteList);
		return resultMap;
	}

	private String getTableName(String smallVersion) {
		String majorVersion = smallVersion.substring(0, 5);
		String tableName = programMapService.getVERSIONMAPPROGRAM().get(majorVersion);
		return tableName;
	}


}
