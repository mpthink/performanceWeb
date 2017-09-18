package com.dell.petshow.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.system.entity.PetProductClass;
import com.dell.petshow.system.entity.vo.PetProductClassVO;

/**
 * <p>
 * 产品分类表 服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public interface IPetProductClassService extends IService<PetProductClass> {

	public List<PetProductClassVO> selectWithPclassName();
}
