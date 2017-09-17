package com.dell.petshow.mapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.system.mapper.PetCpuRawMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class PetCPURAWMapperTest {

	@Autowired
	private PetCpuRawMapper petCpuMapper;


	@Test
	public void initRawData() throws IOException, ParseException {
		Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-9-16");//定义起始日期

		Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse("2017-9-17");//定义结束日期

		Calendar dd = Calendar.getInstance();//定义日期实例

		dd.setTime(d1);//设置日期起始时间

		while (dd.getTime().before(d2)) {//判断是否到结束日期

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String str = sdf.format(dd.getTime());

			System.out.println(str);//输出日期结果

			dd.add(Calendar.MINUTE, 1);//进行当前日期月份加1


		}
	}

}
