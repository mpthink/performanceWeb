package com.dell.petshow.system.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.dell.petshow.system.entity.PetCpuHour;
import com.dell.petshow.system.mapper.PetCpuHourMapper;
import com.dell.petshow.system.mapper.PetCpuRawMapper;
import com.dell.petshow.system.service.IPetCpuHourService;

/**
 * <p>
 * CPU 性能聚合表-小时 服务实现类
 * </p>
 *
 * @author mpthink
 * @since 2017-09-17
 */
@Service
public class PetCpuHourServiceImpl extends ServiceImpl<PetCpuHourMapper, PetCpuHour> implements IPetCpuHourService {

	@Autowired
	private PetCpuRawMapper petCpuRawMapper;

	@Autowired
	private PetCpuHourMapper petCpuHourMapper;

	@Override
	public void cronForCpuHourData() {
		//get product List
		List<Long> productIds = petCpuRawMapper.getProductList();

		for (Long productId : productIds) {
			Date aggBeginTime = petCpuHourMapper.getLastestInsertTimeByProductId(productId);
			List<Map<String, Object>> hourDatas = petCpuRawMapper.generateHourListWithPIDAndBeginTime(productId, aggBeginTime);
			for (int i = 0; i < hourDatas.size(); i++) {
				Map<String, Object> hourData = hourDatas.get(i);
				//order by time asc, the first record may be needed to correct
				//System.err.println(hourData);
				PetCpuHour petCpuHour = new PetCpuHour();
				Date gmtGenerate = null;
				try {
					gmtGenerate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(hourData.get("agg_hour").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Double cpuUtilization = (Double) hourData.get("cpu_utilization_hour");
				petCpuHour.setProductId(productId);
				petCpuHour.setGmtGenerate(gmtGenerate);
				if (i == 0) {
					PetCpuHour temp = petCpuHourMapper.selectOne(petCpuHour);
					if (temp != null) {
						petCpuHour.setId(temp.getId());
						petCpuHour.setCpuUtilization(cpuUtilization);
						petCpuHour.updateById();
					}
				} else {
					petCpuHour.setCpuUtilization(cpuUtilization);
					petCpuHour.insert();
				}
			}

		}
	}
}
