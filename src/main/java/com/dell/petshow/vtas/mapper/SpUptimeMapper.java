package com.dell.petshow.vtas.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.vtas.entity.SpUptime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
public interface SpUptimeMapper extends BaseMapper<SpUptime> {
	SpUptime selectLatestOneByArray(@Param("arrayName") String arrayName);

	SpUptime selectLatestOneByArrayAndSPType(@Param("arrayName") String arrayName, @Param("spType") String spType);

	List<String> selectDistinctArrayListByProgram(@Param("bigVersion") String bigVersion);

	Map<String, Object> selectMaxHourByProgramAndArray(@Param("bigVersion") String bigVersion, @Param("arrayName") String arrayName);
}
