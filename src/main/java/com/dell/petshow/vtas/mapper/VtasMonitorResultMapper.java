package com.dell.petshow.vtas.mapper;

import com.dell.petshow.vtas.entity.VtasMonitorResult;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 监控结果记录表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-12-07
 */
@Repository
@Mapper
public interface VtasMonitorResultMapper extends BaseMapper<VtasMonitorResult> {

}