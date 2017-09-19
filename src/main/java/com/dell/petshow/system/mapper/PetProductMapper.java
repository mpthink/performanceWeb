package com.dell.petshow.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.dell.petshow.system.entity.PetProduct;
import com.dell.petshow.system.entity.vo.PetProductVO;

/**
 * <p>
  * 产品表 Mapper 接口
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public interface PetProductMapper extends BaseMapper<PetProduct> {
	List<PetProductVO> selectWithClassName();
}
