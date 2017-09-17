package com.dell.petshow.common.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.cache.annotation.Cacheable;

public class CrawlerHelpForCloudLab {

	/**
	 * General CloudLabs
	 */
	private final static int ONETOOL_PROJECT = 1;
	private final static String DOMAIN = "NLE";
	private final static String USERNAME = "tempuser";
	private final static String PASSWORD = "Passw0rd";
	private final static String LOGINURL = "https://onetool.netact.nsn-rdnet.net/ajax/processlogin.php";
	private static Map<String, String> userCookies;

	@Cacheable("cookieCache")
	public static Map<String, String> getCookies() throws IOException {
		Map<String, String> datas = new HashMap<>();
		datas.put("project", Integer.toString(ONETOOL_PROJECT));
		datas.put("domain", DOMAIN);
		datas.put("username", USERNAME);
		datas.put("password", PASSWORD);
		Response loginResponse;
		loginResponse = Jsoup.connect(LOGINURL).validateTLSCertificates(false).data(datas).method(Connection.Method.POST).execute();
		userCookies = loginResponse.cookies();
		return userCookies;
	}

}
