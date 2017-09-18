package com.dell.petshow.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.system.entity.PetProductClass;
import com.dell.petshow.system.entity.vo.PetProductClassVO;

/**
 * <p>
  * 产品分类表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public interface PetProductClassMapper extends BaseMapper<PetProductClass> {
	List<PetProductClassVO> selectWithPclassName();
}
