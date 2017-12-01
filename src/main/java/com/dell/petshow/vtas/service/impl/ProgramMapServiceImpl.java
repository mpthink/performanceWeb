package com.dell.petshow.vtas.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.vtas.entity.ProgramMap;
import com.dell.petshow.vtas.mapper.ProgramMapMapper;
import com.dell.petshow.vtas.service.IProgramMapService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

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
	@Cacheable(value = "programCache", key = "methodName")
	public Map<String, String> getVERSIONMAPPROGRAM() {
		Map<String, String> versionMapProgram = new HashMap<>();
		List<ProgramMap> programMaps = selectAll();
		for (ProgramMap programMap : programMaps) {
			versionMapProgram.put(programMap.getMajorVersion(), programMap.getProgram());
		}
		return versionMapProgram;
	}

	@Override
	@Cacheable(value = "programCache", key = "methodName")
	public List<ProgramMap> selectAll() {
		Wrapper<ProgramMap> eWrapper = new EntityWrapper<>();
		eWrapper.orderBy("MAJOR_VERSION", false);
		return programMapMapper.selectList(eWrapper);
	}

	/**
	 * flag: true, use version as ID value
	 *       false, use program name as ID value, it also uses for table name
	 */
	@Override
	@Cacheable(value = "programCache")
	public List<Map<String, Object>> selectAllForSelect2(final boolean flag) {
		Wrapper<ProgramMap> eWrapper = new EntityWrapper<>();
		eWrapper.orderBy("MAJOR_VERSION", false);
		List<ProgramMap> programMaps = programMapMapper.selectList(eWrapper);
		List<Map<String, Object>> listMap = Lists.transform(programMaps, new Function<ProgramMap, Map<String, Object>>() {
			@Override
			public Map<String, Object> apply(ProgramMap programMap) {
				Map<String, Object> map = new HashMap<>();
				if (flag) {
					map.put("id", programMap.getMajorVersion());
				} else {
					map.put("id", programMap.getProgram());
				}
				map.put("text", programMap.getProgram());
				return map;
			}
		});
		return listMap;
	}
}
