package com.dell.petshow.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.system.entity.NetactLab;

/**
 * <p>
 * Lab 信息表 服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public interface INetactLabService extends IService<NetactLab> {
	List<NetactLab> selectByUserId(Long userId);
}
