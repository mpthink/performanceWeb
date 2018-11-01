package com.dell.petshow.vtas.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.VersionDateMap;
import org.springframework.stereotype.Repository;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-11-14
 */
@Repository
@Mapper
public interface VersionDateMapMapper extends BaseMapper<VersionDateMap> {
	VersionDateMap selectByVersion(@Param("version") String version);
}
