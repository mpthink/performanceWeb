package com.dell.petshow.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.system.entity.NetactLab;

/**
 * <p>
  * Lab 信息表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public interface NetactLabMapper extends BaseMapper<NetactLab> {
	List<NetactLab> selectByUserId(@Param("userId") Long userId);
}
