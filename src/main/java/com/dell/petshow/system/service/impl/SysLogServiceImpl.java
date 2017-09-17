package com.dell.petshow.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.system.entity.SysLog;
import com.dell.petshow.system.mapper.SysLogMapper;
import com.dell.petshow.system.service.ISysLogService;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements ISysLogService {
	
}
