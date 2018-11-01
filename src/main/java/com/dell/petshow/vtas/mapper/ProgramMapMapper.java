package com.dell.petshow.vtas.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.ProgramMap;
import org.springframework.stereotype.Repository;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Repository
@Mapper
public interface ProgramMapMapper extends BaseMapper<ProgramMap> {
	ProgramMap selectOneBasedonVersion(@Param("bigVersion") String bigVersion);
}
