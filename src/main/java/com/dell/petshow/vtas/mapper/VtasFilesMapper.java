package com.dell.petshow.vtas.mapper;

import com.dell.petshow.vtas.entity.VtasFiles;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  * 文件管理表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-12-01
 */
@Repository
@Mapper
public interface VtasFilesMapper extends BaseMapper<VtasFiles> {

}