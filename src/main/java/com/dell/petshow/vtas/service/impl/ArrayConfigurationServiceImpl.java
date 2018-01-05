package com.dell.petshow.vtas.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.ArrayConfiguration;
import com.dell.petshow.vtas.mapper.ArrayConfigurationMapper;
import com.dell.petshow.vtas.service.IArrayConfigurationService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2018-01-02
 */
@Service
public class ArrayConfigurationServiceImpl extends ServiceImpl<ArrayConfigurationMapper, ArrayConfiguration> implements IArrayConfigurationService {

	@Autowired
	private ArrayConfigurationMapper arrayConfigurationMapper;

	@Override
	public List<Map<String, Object>> selectLatestArrayConfiguration() {
		return arrayConfigurationMapper.selectLatestArrayConfiguration();
	}

	@Override
	public List<Map<String, Object>> selectTimeForSelect2() {
		List<String> times = arrayConfigurationMapper.selectPollTime();
		List<Map<String, Object>> listMap = new ArrayList<>();
		for (String time : times) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", time);
			map.put("text", time);
			listMap.add(map);
		}
		return listMap;
	}

	@Override
	public List<Map<String, Object>> getArrayConfigurationByTime(String time) {
		return arrayConfigurationMapper.getArrayConfigurationByTime(time);
	}

}
