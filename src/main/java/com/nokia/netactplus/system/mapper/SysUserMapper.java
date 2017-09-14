package com.nokia.netactplus.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.nokia.netactplus.system.entity.SysUser;

/**
 * <p>
  * 用户表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-04-17
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

	List<SysUser> selectUserList(@Param("search") String search);

}
