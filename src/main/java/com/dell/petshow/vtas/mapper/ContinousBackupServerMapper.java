package com.dell.petshow.vtas.mapper;

import com.dell.petshow.vtas.entity.ContinousBackupServer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2018-03-26
 */
@Repository
@Mapper
public interface ContinousBackupServerMapper extends BaseMapper<ContinousBackupServer> {

}