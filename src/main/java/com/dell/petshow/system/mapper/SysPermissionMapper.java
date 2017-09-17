package com.dell.petshow.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.system.entity.SysPermission;

/**
 * <p>
  * 权限表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

	List<SysPermission> selectMenuByuserIdAndPid(@Param("userId") Long userId, @Param("pid") Long pid);

	List<String> selectPermCodesByuserId(Long userId);
}
