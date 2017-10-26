package com.dell.petshow.mapper;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.vtas.entity.NiceNameMap;
import com.dell.petshow.vtas.mapper.JobRuntimeMapper;
import com.dell.petshow.vtas.mapper.NiceNameMapMapper;
import com.dell.petshow.vtas.mapper.ProgramMapMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class VTASMapperTest {

	@Autowired
	private NiceNameMapMapper niceNameMapMapper;
	@Autowired
	private JobRuntimeMapper jobRuntimeMapper;
	@Autowired
	private ProgramMapMapper programMapMapper;

	//@Test
	public void niceNameMapperTest() {
		List<NiceNameMap> nameList = niceNameMapMapper.selectNiceNameByVersionAndArray("4.3.0", "test");
		for (NiceNameMap name : nameList) {
			System.out.println(name.getVersion() + " " + name.getNiceName());
		}
	}

	//@Test
	public void jobRuntimeMapperTest() {
		List<String> teStrings = jobRuntimeMapper.selectDistinctArrayListByProgram("4.3.0");
		for (String temp : teStrings) {
			System.out.println(temp);
		}
	}
}
