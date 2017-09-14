package com.nokia.netactplus.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseSpider {

	private final static Logger LOGGER = LoggerFactory.getLogger(BaseSpider.class);
	private final static String LOGINURL = "https://confluence.int.net.nokia.com/login.action";
	private static String userName = "p1ma";
	private static String userPassword = "Passion20171";
	private static Map<String, String> userCookies;
	private static BaseSpider instance;

	//singleton mode
	private BaseSpider() {
		generateUserCookies();
	}

	public static synchronized BaseSpider getInstance() {
		if (instance == null) {
			instance = new BaseSpider();
		}
		return instance;
	}

	public Document getPage(String url) {
		Document document = null;
		try {
			document = Jsoup.connect(url).cookies(userCookies).get();
		} catch (IOException e) {
			System.err.println("page not found!");
			return null;
		}
		return document;
	}

	private void generateUserCookies() {
		Map<String, String> datas = new HashMap<>();
		datas.put("os_username", userName);
		datas.put("os_password", userPassword);
		try {
			Response loginResponse = Jsoup.connect(LOGINURL).data(datas).method(Connection.Method.POST).execute();
			userCookies = loginResponse.cookies();
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.debug("user Cookies is generated.");
	}


}
