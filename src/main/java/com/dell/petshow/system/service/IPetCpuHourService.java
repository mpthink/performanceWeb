package com.dell.petshow.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.system.entity.PetCpuHour;

/**
 * <p>
 * CPU 性能聚合表-小时 服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public interface IPetCpuHourService extends IService<PetCpuHour> {

	public void cronJobForCpuHourData();

}
