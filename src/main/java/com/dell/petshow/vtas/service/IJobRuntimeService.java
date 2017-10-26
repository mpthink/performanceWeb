package com.dell.petshow.vtas.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.vtas.entity.JobRuntime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
public interface IJobRuntimeService extends IService<JobRuntime> {

	List<String> selectDistinctArrayList();

	List<String> selectDistinctArrayListByProgram(String bigVersion);
}
