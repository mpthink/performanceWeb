package com.dell.petshow.vtas.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.NiceNameMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
public interface NiceNameMapMapper extends BaseMapper<NiceNameMap> {
	List<NiceNameMap> selectNiceNameByVersionAndArray(@Param("bigVersion") String bigVersion, @Param("arrayName") String arrayName);
}
