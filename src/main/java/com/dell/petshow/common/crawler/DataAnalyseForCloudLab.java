package com.dell.petshow.common.crawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class DataAnalyseForCloudLab {

	public static String getTrTdDataFromTable(Element table, int trNum, int tdNum) {
		Elements trs = table.getElementsByTag("tr");
		Element tr = trs.get(trNum - 1);
		Elements tds = tr.getElementsByTag("td");
		Element td = tds.get(tdNum - 1);
		return td.text().trim();
	}

	public static List<String> listTdDataFromTable(Element table, int tdNum) {
		List<String> tdDatas = new ArrayList<>();
		Elements trs = table.getElementsByTag("tr");
		//System.out.println("******debug *********");
		//System.out.println(trs.size());
		//System.out.println("******debug *********");
		int rowNumber=1;
		trs.remove(0);
		for (Element tr : trs) {
			Elements tds = tr.getElementsByTag("td");
			Element td = tds.get(tdNum - 1);
			tdDatas.add(td.text().trim());
		}
		//System.out.println("******debug *********");
		//System.out.println(tdDatas.size());
		//System.out.println("******debug *********");
		return tdDatas;
	}
}
