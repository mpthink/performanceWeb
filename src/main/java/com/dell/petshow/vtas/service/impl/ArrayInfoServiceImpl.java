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
import com.dell.petshow.vtas.entity.ArrayInfo;
import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.entity.SpUptime;
import com.dell.petshow.vtas.mapper.ArrayInfoMapper;
import com.dell.petshow.vtas.mapper.ProgramMapMapper;
import com.dell.petshow.vtas.mapper.SpUptimeMapper;
import com.dell.petshow.vtas.mapper.VersionDateMapMapper;
import com.dell.petshow.vtas.service.IArrayInfoService;



/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-11-01
 */
@Service
public class ArrayInfoServiceImpl extends ServiceImpl<ArrayInfoMapper, ArrayInfo> implements IArrayInfoService {

	@Autowired
	private ArrayInfoMapper arrayInfoMapper;
	@Autowired
	private SpUptimeMapper spUptimeMapper;
	@Autowired
	private ProgramMapMapper programMapMapper;
	@Autowired
	private VersionDateMapMapper versionDateMapMapper;

	@Cacheable(value = "commonCache", key = "methodName")
	@Override
	public List<Map<String, Object>> getArrayWithUptime() {
		List<Map<String, Object>> listMaps = new ArrayList<>();
		Wrapper<ArrayInfo> aWrapper = new EntityWrapper<>();
		aWrapper.ne("USAGE_TYPE", "cndu");
		List<ArrayInfo> arrayInfos = arrayInfoMapper.selectList(aWrapper);
		for (ArrayInfo arrayInfo : arrayInfos) {
			String arrayName = arrayInfo.getArrayName();
			String model = arrayInfo.getModel();
			String status = arrayInfo.getArrayStatus();
			String comments = arrayInfo.getComment();
			String tfa = arrayInfo.getTfa();
			Integer serviceTime = arrayInfo.getServiceTime();
			SpUptime spUptime = spUptimeMapper.selectLatestOneByArray(arrayName);
			if (spUptime != null) {
				String smallVersion = spUptime.getVersion();
				Integer upTime = spUptime.getUptime();
				ProgramMap programMap = programMapMapper.selectOneBasedonVersion(smallVersion.substring(0, 5));
				String programName = programMap.getProgram();
				String versionTime = versionDateMapMapper.selectByVersion(smallVersion).getDate();
				Map<String, Object> map = new HashMap<>();
				map.put("programName", programName);
				map.put("arrayName", arrayName);
				map.put("tfa", tfa);
				map.put("versionTime", versionTime);
				map.put("model", model);
				map.put("smallVersion", smallVersion);
				map.put("upTime", upTime);
				map.put("serviceTime", serviceTime);
				map.put("status", status);
				map.put("comments", comments);
				listMaps.add(map);
			}
		}

		return listMaps;
	}


	@Override
	@Cacheable(value = "commonCache", key = "methodName")
	public List<Map<String, Object>> getArrayNumForAllPrograms() {
		List<Map<String, Object>> result = new ArrayList<>();
		Wrapper<ProgramMap> eWrapper = new EntityWrapper<>();
		eWrapper.orderBy("MAJOR_VERSION", false);
		List<ProgramMap> programMaps = programMapMapper.selectList(eWrapper);
		List<ArrayInfo> arrayInfos = arrayInfoMapper.selectList(null);
		Map<String, Integer> versionMap = new HashMap<>();
		for (ArrayInfo arrayInfo : arrayInfos) {
			String arrayName = arrayInfo.getArrayName();
			SpUptime spUptime = spUptimeMapper.selectLatestOneByArray(arrayName);
			String latestVersion = spUptime.getVersion();
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
				if (key.startsWith(bigVersion)) {
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
