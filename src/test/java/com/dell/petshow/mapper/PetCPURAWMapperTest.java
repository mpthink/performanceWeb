package com.dell.petshow.mapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.entity.PetCpuRaw;
import com.dell.petshow.system.mapper.PetCpuRawMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class PetCPURAWMapperTest {

	@Autowired
	private PetCpuRawMapper petCpuMapper;

	@Test
	public void testGenerateHourData() throws ParseException {
		Long productId = 911147525042515968L;
		Date aggBeginTime = new SimpleDateFormat("yyyy-MM-dd").parse("2017-9-20");
		List<Map<String, Object>> aggDatas = petCpuMapper.generateHourListWithPIDAndBeginTime(productId, aggBeginTime);
		for (Map<String, Object> aggData : aggDatas) {
			System.out.println(aggData.get("product_id") + " " + aggData.get("agg_hour") + " " + aggData.get("cpu_utilization_hour"));
		}
	}

	public void initRawData() throws IOException, ParseException {

		Long productId = 911147525042515968L;
		Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-9-20");//定义起始日期
		Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-9-22");//定义结束日期
		Calendar dd = Calendar.getInstance();//定义日期实例
		dd.setTime(d1);//设置日期起始时间
		while (dd.getTime().before(d2)) {//判断是否到结束日期
			//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//String str = sdf.format(dd.getTime());
			//System.out.println(str);//输出日期结果
			PetCpuRaw raw = new PetCpuRaw();
			raw.setProductId(productId);
			raw.setGmtGenerate(dd.getTime());
			raw.setCpuUtilization(getRandomNum());
			raw.insert();
			dd.add(Calendar.MINUTE, 1);//进行当前日期加1分钟，可以加小时，日，月
		}
	}

	private double getRandomNum() {
		double random = Math.random() * 100;
		BigDecimal bd = new BigDecimal(random);
		double trans = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return trans;
	}


}
