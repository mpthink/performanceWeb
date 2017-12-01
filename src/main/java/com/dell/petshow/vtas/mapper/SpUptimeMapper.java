package com.dell.petshow.vtas.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.SpUptime;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-11-01
 */
public interface SpUptimeMapper extends BaseMapper<SpUptime> {
	SpUptime selectLatestOneByArray(@Param("arrayName") String arrayName);

	SpUptime selectLatestOneByArrayAndSPType(@Param("arrayName") String arrayName, @Param("spType") String spType);
}
