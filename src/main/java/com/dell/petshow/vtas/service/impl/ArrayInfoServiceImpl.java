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
import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.entity.SpUptime;
import com.dell.petshow.vtas.mapper.ArrayInfoMapper;
import com.dell.petshow.vtas.mapper.ProgramMapMapper;
import com.dell.petshow.vtas.mapper.SpUptimeMapper;
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

	@Cacheable(value = "commonCache", key = "methodName")
	@Override
	public List<Map<String, Object>> getArrayWithUptime() {
		List<Map<String, Object>> listMaps = new ArrayList<>();
		Wrapper<ArrayInfo> wrapper = new EntityWrapper<>();
		wrapper.in("usage_type", "baseline,100days");
		List<ArrayInfo> arrayInfos = arrayInfoMapper.selectList(wrapper);
		for (ArrayInfo arrayInfo : arrayInfos) {
			String arrayName = arrayInfo.getArrayName();
			String model = arrayInfo.getModel();
			String status = arrayInfo.getArrayStatus();
			String comments = arrayInfo.getComment();
			SpUptime spUptime = spUptimeMapper.selectLatestOneByArray(arrayName);
			if (spUptime != null) {
				String smallVersion = spUptime.getVersion();
				Integer upTime = spUptime.getUptime();
				ProgramMap programMap = programMapMapper.selectOneBasedonVersion(smallVersion.substring(0, 5));
				String programName = programMap.getProgram();
				Map<String, Object> map = new HashMap<>();
				map.put("programName", programName);
				map.put("arrayName", arrayName);
				map.put("model", model);
				map.put("smallVersion", smallVersion);
				map.put("upTime", upTime);
				map.put("status", status);
				map.put("comments", comments);
				listMaps.add(map);
			}
		}

		return listMaps;
	}

}
