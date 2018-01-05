package com.dell.petshow.vtas.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.vtas.entity.ArrayConfiguration;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mpthink
 * @since 2018-01-02
 */
public interface IArrayConfigurationService extends IService<ArrayConfiguration> {

	List<Map<String, Object>> selectLatestArrayConfiguration();

	List<Map<String, Object>> selectTimeForSelect2();

	List<Map<String, Object>> getArrayConfigurationByTime(String time);

}
