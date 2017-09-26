package com.dell.petshow.system.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.system.entity.PetCpuRaw;

/**
 * <p>
  * CPU 性能原始数据表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public interface PetCpuRawMapper extends BaseMapper<PetCpuRaw> {
	List<Map<String, Object>> generateHourListWithPIDAndBeginTime(@Param("productId") Long productId, @Param("aggBeginTime") Date aggBeginTime);

	List<Map<String, Object>> generateHourList();

	List<Long> getProductList();

}
