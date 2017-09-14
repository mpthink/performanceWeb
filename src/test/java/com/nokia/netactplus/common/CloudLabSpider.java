package com.nokia.netactplus.common;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nokia.netactplus.system.entity.NetactLab;


public class CloudLabSpider implements Runnable {

	private final static Logger LOGGER = LoggerFactory.getLogger(CloudLabSpider.class);
	private String prefixOfCloudLabUrl = "https://confluence.int.net.nokia.com/display/OSSOPLSVL/";
	private static BaseSpider baseSpider = BaseSpider.getInstance();
	public static List<String> notFoundList = new LinkedList<String>();
	public static List<String> invalidList = new LinkedList<String>();
	public static List<String> otherList = new LinkedList<String>();
	public static List<String> validList = new LinkedList<String>();
	private int labNum;

	public CloudLabSpider(int labNum) {
		this.labNum = labNum;
	}

	@Override
	public void run() {
		getCloudLabInfo(labNum);
	}


	private void getCloudLabInfo(int labNum) {
		String cloudLabName;
		if (labNum < 10) {
			cloudLabName = "CloudLab00" + Integer.toString(labNum);
		} else if (labNum >= 10 && labNum < 100) {
			cloudLabName = "CloudLab0" + Integer.toString(labNum);
		} else {
			cloudLabName = "CloudLab" + Integer.toString(labNum);
		}
		String cloudLabUrl = prefixOfCloudLabUrl + cloudLabName;
		Document document = baseSpider.getPage(cloudLabUrl);
		int labType = checkCloudLabType(document);
		System.out.println("Lab name: " + cloudLabName);
		switch (labType) {
			case 0:
				notFoundList.add(cloudLabName);
				break;
			case 1:
				invalidList.add(cloudLabName);
				break;
			case 2:
				validList.add(cloudLabName);
				NetactLab lab = extractDataFromCloudLab(document);
				lab.setGmtCreate(new Date());
				lab.setLabName(cloudLabName);
				lab.setLabType(labType);
				lab.setLabUrl(cloudLabUrl);
				lab.insert();
				break;
			case 3:
				otherList.add(cloudLabName);
				break;
		}
		//		if (labType == 2) {
		//			NetactLab lab = extractDataFromCloudLab(document);
		//			lab.setLabName(cloudLabName);
		//			boolean result = lab.insert();
		//			if (result == true) {
		//				System.out.println("insert success");
		//			} else {
		//				System.err.println("insert failed");
		//			}
		//			System.out.println(lab.toString());
		//		}
	}


	private NetactLab extractDataFromCloudLab(Document document) {
		NetactLab labinfo = new NetactLab();
		Elements tables = document.getElementsByClass("confluenceTable");
		for (Element table : tables) {
			String tableContent = table.html();
			if (tableContent.toLowerCase().contains("viis")) {
				Elements trRows = table.getElementsByTag("tr");
				for (Element trRow : trRows) {
					Elements tdColumns = trRow.getElementsByTag("td");
					if (trRow.html().toLowerCase().contains("vm-1")) {
						labinfo.setDbIpv4(tdColumns.get(3).text());
					} else if (trRow.html().toLowerCase().contains("viis")) {
						labinfo.setViisIpv4(tdColumns.get(3).text());
					}
				}
			} else if (tableContent.toLowerCase().contains("lbwas")) {
				Elements trRows = table.getElementsByTag("tr");
				for (Element trRow : trRows) {
					Elements tdColumns = trRow.getElementsByTag("td");
					if (trRow.html().toLowerCase().contains("lbwas")) {
						labinfo.setLbwasIpv4(tdColumns.get(1).text());
					}
				}
			} else if (tableContent.toLowerCase().contains("vda-1")) {
				Elements trRows = table.getElementsByTag("tr");
				for (Element trRow : trRows) {
					Elements tdColumns = trRow.getElementsByTag("td");
					if (trRow.html().toLowerCase().contains("vda-1")) {
						labinfo.setVda1Ipv4(tdColumns.get(3).text());
					}
				}
			}
		}
		return labinfo;
	}

	private int checkCloudLabType(Document document) {
		if (document == null) {
			//page不存在
			return 0;
		}
		Elements tables = document.getElementsByClass("confluenceTable");
		if (tables.size() == 0) {
			//lab 无效
			return 1;
		} else if (document.html().toLowerCase().contains("configuration")) {
			//页面有lab Configuration
			return 2;
		} else {
			//页面中没有lab configuration,但有其他内容
			return 3;
		}
	}

}
