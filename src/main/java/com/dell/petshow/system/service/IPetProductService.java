package com.dell.petshow.system.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.dell.petshow.system.entity.PetProduct;
import com.dell.petshow.system.entity.vo.PetProductVO;

/**
 * <p>
 * 产品表 服务类
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
public interface IPetProductService extends IService<PetProduct> {
	List<PetProductVO> selectWithClassName();
}
