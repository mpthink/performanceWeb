package com.dell.petshow.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.system.entity.PetProductClass;
import com.dell.petshow.system.entity.vo.PetProductClassVO;
import com.dell.petshow.system.mapper.PetProductClassMapper;
import com.dell.petshow.system.service.IPetProductClassService;

/**
 * <p>
 * 产品分类表 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
@Service
public class PetProductClassServiceImpl extends ServiceImpl<PetProductClassMapper, PetProductClass> implements IPetProductClassService {

	@Autowired
	private PetProductClassMapper petProductClassMapper;

	@Override
	public List<PetProductClassVO> selectWithPclassName() {
		return petProductClassMapper.selectWithPclassName();
	}

}
