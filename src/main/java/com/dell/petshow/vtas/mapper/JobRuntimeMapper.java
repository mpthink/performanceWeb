package com.dell.petshow.vtas.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.JobRuntime;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
public interface JobRuntimeMapper extends BaseMapper<JobRuntime> {

	List<String> selectDistinctArrayList();

}
