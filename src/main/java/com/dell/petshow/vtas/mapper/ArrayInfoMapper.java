package com.dell.petshow.vtas.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.ArrayInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-11-01
 */
@Repository
@Mapper
public interface ArrayInfoMapper extends BaseMapper<ArrayInfo> {

}