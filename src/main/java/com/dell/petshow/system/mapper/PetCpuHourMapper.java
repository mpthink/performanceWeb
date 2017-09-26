package com.dell.petshow.system.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.system.entity.PetCpuHour;

/**
 * <p>
  * CPU 性能聚合表-小时 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public interface PetCpuHourMapper extends BaseMapper<PetCpuHour> {
	List<PetCpuHour> selectLastestRecord();

	Date getLastestInsertTimeByProductId(@Param("productId") Long productId);
}
