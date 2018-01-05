package com.dell.petshow.vtas.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.ArrayConfiguration;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2018-01-02
 */
public interface ArrayConfigurationMapper extends BaseMapper<ArrayConfiguration> {

	List<Map<String, Object>> selectLatestArrayConfiguration();

	List<String> selectPollTime();

	List<Map<String, Object>> getArrayConfigurationByTime(@Param("time") String time);

}
