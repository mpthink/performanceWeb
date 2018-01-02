package com.dell.petshow.vtas.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.SpUptime;
import com.dell.petshow.vtas.mapper.SpUptimeMapper;
import com.dell.petshow.vtas.service.ISpUptimeService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-11-01
 */
@Service
public class SpUptimeServiceImpl extends ServiceImpl<SpUptimeMapper, SpUptime> implements ISpUptimeService {

	@Autowired
	private SpUptimeMapper spUptimeMapper;

	@Override
	public List<String> selectDistinctArrayListByProgram(String bigVersion) {
		return spUptimeMapper.selectDistinctArrayListByProgram(bigVersion);
	}

	@Override
	public Map<String, Object> selectMaxHourByProgramAndArray(String bigVersion, String arrayName) {
		return spUptimeMapper.selectMaxHourByProgramAndArray(bigVersion, arrayName);
	}

}
