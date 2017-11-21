package com.dell.petshow.mapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.entity.PetCpuHour;
import com.dell.petshow.system.mapper.PetCpuHourMapper;
import com.dell.petshow.system.mapper.PetCpuRawMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class PetCPUHourMapperTest {

	@Autowired
	private PetCpuRawMapper petCpuMapper;

	@Autowired
	private PetCpuHourMapper petCpuHourMapper;

	@Test
	public void dummy() {}

	//@Test
	public void generateHourData() throws IOException, ParseException {

		//		List<PetCpuRaw> rawDatas = petCpuMapper.generateHourList();
		//		for (PetCpuRaw rawData : rawDatas) {
		//			PetCpuHour petCpuHour = new PetCpuHour();
		//			petCpuHour.setProductId(rawData.getProductId());
		//			petCpuHour.setGmtGenerate(rawData.getGmtGenerate());
		//			petCpuHour.setCpuUtilization(rawData.getCpuUtilization());
		//			petCpuHour.insert();
		//		}

		List<PetCpuHour> results = petCpuHourMapper.selectLastestRecord();
		for (PetCpuHour petCpuHour : results) {
			System.err.println(petCpuHour.getGmtGenerate());
		}

	}

}
