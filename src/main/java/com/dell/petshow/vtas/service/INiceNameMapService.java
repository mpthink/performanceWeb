package com.dell.petshow.vtas.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.vtas.entity.NiceNameMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
public interface INiceNameMapService extends IService<NiceNameMap> {
	List<NiceNameMap> selectNiceNameByVersionAndArray(String bigVersion, String arrayName);
}
