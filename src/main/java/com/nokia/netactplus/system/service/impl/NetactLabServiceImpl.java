package com.nokia.netactplus.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.nokia.netactplus.system.entity.NetactLab;
import com.nokia.netactplus.system.mapper.NetactLabMapper;
import com.nokia.netactplus.system.service.INetactLabService;

/**
 * <p>
 * Lab 信息表 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Service
public class NetactLabServiceImpl extends ServiceImpl<NetactLabMapper, NetactLab> implements INetactLabService {

	@Override
	public List<NetactLab> selectByUserId(Long userId) {
		return baseMapper.selectByUserId(userId);
	}

}
