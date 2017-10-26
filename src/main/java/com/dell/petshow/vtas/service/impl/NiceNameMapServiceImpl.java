package com.dell.petshow.vtas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.NiceNameMap;
import com.dell.petshow.vtas.mapper.NiceNameMapMapper;
import com.dell.petshow.vtas.service.INiceNameMapService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Service
public class NiceNameMapServiceImpl extends ServiceImpl<NiceNameMapMapper, NiceNameMap> implements INiceNameMapService {

	@Autowired
	private NiceNameMapMapper niceNameMapMapper;

	@Override
	@Cacheable(value = "commonCache")
	public List<NiceNameMap> selectNiceNameByVersionAndArray(String bigVersion, String arrayName) {
		return niceNameMapMapper.selectNiceNameByVersionAndArray(bigVersion, arrayName);
	}

}
