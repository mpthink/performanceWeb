package com.dell.petshow.vtas.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.vtas.entity.ArrayInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-11-01
 */
public interface IArrayInfoService extends IService<ArrayInfo> {
	List<Map<String, Object>> getArrayWithUptime();
}
