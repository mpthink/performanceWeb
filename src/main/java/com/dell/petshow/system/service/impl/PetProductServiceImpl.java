package com.dell.petshow.system.service.impl;

import com.dell.petshow.system.entity.PetProduct;
import com.dell.petshow.system.mapper.PetProductMapper;
import com.dell.petshow.system.service.IPetProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
	
}
