package com.dell.petshow.vtas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.JobRuntime;
import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.mapper.JobRuntimeMapper;
import com.dell.petshow.vtas.mapper.ProgramMapMapper;
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

	@Autowired
	private ProgramMapMapper programMapMapper;

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
	@Cacheable(value = "commonCache", key = "methodName")
	public List<Map<String, Object>> getArrayNumForAllPrograms() {
		List<Map<String, Object>> result = new ArrayList<>();
		Wrapper<ProgramMap> eWrapper = new EntityWrapper<>();
		eWrapper.orderBy("MAJOR_VERSION", false);
		List<ProgramMap> programMaps = programMapMapper.selectList(eWrapper);
		List<String> arrays = jobRuntimeMapper.selectDistinctArrayList();
		Map<String, Integer> versionMap = new HashMap<>();
		for (String array : arrays) {
			String latestVersion = jobRuntimeMapper.selectLatestVersionByArray(array);
			if (versionMap.containsKey(latestVersion)) {
				versionMap.put(latestVersion, versionMap.get(latestVersion) + 1);
			} else {
				versionMap.put(latestVersion, 1);
			}
		}
		int i = 0;
		for (ProgramMap programMap : programMaps) {
			//only display the latest 6 records
			if (i > 5) {
				break;
			}
			Map<String, Object> map = new HashMap<>();
			StringBuffer buffer = new StringBuffer();
			String programName = programMap.getProgram();
			String bigVersion = programMap.getMajorVersion();
			int sum = 0;
			for (Entry<String, Integer> entry : versionMap.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				if (key.contains(bigVersion)) {
					sum = sum + value;
					buffer.append(key).append("  ").append(value).append(";");
				}
			}
			if (sum > 0) {
				map.put("programCount", programName + "  " + sum);
				map.put("versionCount", buffer.toString());
				result.add(map);
				i++;
			}
		}
		return result;
	}

}
