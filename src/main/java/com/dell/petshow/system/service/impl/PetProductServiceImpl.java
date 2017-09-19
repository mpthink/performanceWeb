package com.dell.petshow.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.system.entity.PetProduct;
import com.dell.petshow.system.entity.vo.PetProductVO;
import com.dell.petshow.system.mapper.PetProductMapper;
import com.dell.petshow.system.service.IPetProductService;

/**
 * <p>
 * 产品表 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
@Service
public class PetProductServiceImpl extends ServiceImpl<PetProductMapper, PetProduct> implements IPetProductService {

	@Autowired
	private PetProductMapper petProductMapper;

	@Override
	public List<PetProductVO> selectWithClassName() {
		return petProductMapper.selectWithClassName();
	}

}
