package com.dell.petshow.vtas.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.mapper.ProgramMapMapper;
import com.dell.petshow.vtas.service.IProgramMapService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-10-17
 */
@Service
public class ProgramMapServiceImpl extends ServiceImpl<ProgramMapMapper, ProgramMap> implements IProgramMapService {
	@Autowired
	private ProgramMapMapper programMapMapper;

	@Override
	@Cacheable(value = "programCache")
	public List<ProgramMap> selectAll() {
		Wrapper<ProgramMap> eWrapper = new EntityWrapper<>();
		eWrapper.orderBy("MAJOR_VERSION", false);
		return programMapMapper.selectList(eWrapper);
	}
}
