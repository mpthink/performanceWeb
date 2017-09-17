package com.dell.petshow.mapper;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dell.petshow.common.crawler.CrawlerForCloudLab;
import com.dell.petshow.system.entity.NetactLab;
import com.dell.petshow.system.mapper.NetactLabMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class NetActLabMapperTest {

	@Autowired
	private NetactLabMapper netactLabMapper;

	private CrawlerForCloudLab crawler = new CrawlerForCloudLab();

	@Test
	public void testSelectList() throws IOException {
		//NetactLab lab = crawler.getLabDetail("clab261");
		NetactLab lab=null;
		//NetactLab test = netactLabMapper.selectOne(lab);
		NetactLab test;
		//if (test == null && lab != null) {
			//lab.setGmtCreate(new Date());
			//lab.insert();
		//}
		List<String> list_web = crawler.getAllLabNames();
		List<String> list_db = crawler.getAllLabNamesFromDB();
		System.out.println("******main debug *********\n");
		System.out.println("******we got new labs from onetool*********\n");
		System.out.print(list_web.size());
		//System.out.println(list_new);
		//System.out.println(list.get(0));
		//String string = "houjiantao";
		//string.toUpperCase();
		//System.out.println(string);
		//System.out.println(string.toUpperCase());
		
		System.out.println("******we got current labs from database*********\n");
		System.out.print(list_db.size());
		System.out.println("******main debug *********");
		System.out.println("******Number of new labs *********");
		List<String> list=crawler.getLabListToInsert(list_web, list_db);
		System.out.println(list);
		for(String labname:list){
			if(labname.contains(" X") || (labname.contains("CLAB")==false)  ) continue;
			
			lab = crawler.getLabDetail(labname);
			test = netactLabMapper.selectOne(lab);
			if (test == null && lab != null) {
				lab.setGmtCreate(new Date());
				lab.insert();
				break;
			}
		}
		System.out.print(crawler.getLabListToInsert(list_web, list_db).size());
		list_web = crawler.getAllLabNames();
		System.out.println("******Number of released labs *********");
		list=crawler.getLabListToDelete( list_web, list_db);
		System.out.println(list.size());
		System.out.println(list);
		for(String labname:list)
		{
			crawler.deleteLabInfoFromDB(labname);
			//System.out.println("delete: CLAB1049"+" \n");
		}
		//System.out.print(crawler.getLabListToDelete( list_web, list_db).size());
		System.out.println("******main debug *********");
		
	}

}
