package com.nokia.netactplus.common.crawler;

import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.druid.util.JdbcUtils;
//import com.mysql.jdbc.Statement;
import com.nokia.netactplus.system.entity.NetactLab;
//import com.sun.jdi.connect.spi.Connection;

public class CrawlerForCloudLab {

	private final String ListLabUrl = "https://onetool.netact.nsn-rdnet.net/ajax/getlabs.php";
	//private final String InternalLabListUrl = "http://localhost:8080/system/lab/list";
	private final String Prefix_LabInfoUrl = "https://onetool.netact.nsn-rdnet.net/ui/index.php?project=General%20CloudLabs&page=Labs&name=";
	private final String Prefix_LabDetailUrl = "https://onetool.netact.nsn-rdnet.net/ajax/getsinglelab.php?name=";
	private final String USERAGENT =
		"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";
    private static String dbDriver=null;
    private static String dbUrl=null;
    private static String dbUser = null;
    private static String dbPass = null;
    
	static {
		try {
			Properties props = new Properties();
			InputStream in = JdbcUtils.class.getResourceAsStream("/properties/jdbc.properties");

			props.load(in);

			dbUrl = props.getProperty("jdbc.url");
			dbUser = props.getProperty("jdbc.username");
			dbPass = props.getProperty("jdbc.password");
			dbDriver = props.getProperty("jdbc.driver");
			 Class.forName(dbDriver);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<String> getAllLabNames() throws IOException {
		Document document =
			Jsoup.connect(ListLabUrl).userAgent(USERAGENT).validateTLSCertificates(false).cookies(CrawlerHelpForCloudLab.getCookies()).get();
		//System.out.println("******debug *********");
		Element table = document.getElementsByTag("table").get(0);
		//System.out.println("******debug *********");
		//System.out.print(table.childNodeSize());
		//System.out.println("******debug *********");
		return DataAnalyseForCloudLab.listTdDataFromTable(table, 3);
		
	}
	
	public List<String> getAllLabNamesFromDB() throws IOException {
		
		//Document document = Jsoup.connect(InternalLabListUrl).userAgent(USERAGENT).validateTLSCertificates(false).cookies(CrawlerHelpForCloudLab.getCookies()).get();
		List<String> list = new ArrayList<>();
		String sql;
		Connection conn = null;
		//Connection conn=null;
		
		try{
		Class.forName(dbDriver);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		try{
			Statement statement = conn.createStatement();
			sql = "select * from netactplusadmin.netact_lab";
			ResultSet rs=statement.executeQuery(sql);
			System.out.print("serch result");
			while (rs.next()){
				//System.out.println(rs.getString(2));
				list.add(rs.getString(2).toUpperCase());
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				System.out.println("close the db connection\n");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return list;
	}
	public void deleteLabInfoFromDB(String LabName) {
		String sql;
		Connection conn = null;
		//Connection conn=null;
		
		try{
		Class.forName(dbDriver);
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		try{
			conn = DriverManager.getConnection(dbUrl,dbUser,dbPass);
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		try{
			Statement statement = conn.createStatement();
			//sql = "select * from netactplusadmin.netact_lab";
			System.out.println(LabName);
			sql = "delete from netactplusadmin.netact_lab where lab_name= '" + LabName + "'";
			System.out.println(sql);
			statement.executeUpdate(sql);
			System.out.print("delete lab: "+LabName+" from DataBase");
		}catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
				System.out.println("close the db connection\n");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
    public List<String> getLabListToInsert(List<String> LabListWeb, List<String> LabListDb) throws IOException{
    	LabListWeb.removeAll(LabListDb);
    	return LabListWeb;
    	    }
    public List<String> getLabListToDelete(List<String> LabListWeb, List<String> LabListDb) throws IOException{
    	LabListDb.removeAll(LabListWeb);
    	return LabListDb;
    	    }
	public NetactLab getLabDetail(String labName) throws IOException {
		String labDetailUrl = Prefix_LabDetailUrl + labName.toUpperCase();
		String labUrl = Prefix_LabInfoUrl + labName.toUpperCase();
		NetactLab netactLab = new NetactLab();
		netactLab.setLabName(labName);
		netactLab.setLabUrl(labUrl);
		Document document =
			Jsoup.connect(labDetailUrl).userAgent(USERAGENT).validateTLSCertificates(false).cookies(CrawlerHelpForCloudLab.getCookies()).get();
		if (document.text().toLowerCase().contains("error!")) {
			return null;
		}
		Elements tables = document.getElementsByTag("table");
		for (Element table : tables) {
			int labType = 0;
			if (table.text().contains("Release date")) {
				String labReleaseDate = DataAnalyseForCloudLab.getTrTdDataFromTable(table, 2, 2);
				netactLab.setGmtRelease(dateFormat(labReleaseDate));
				continue;
			} else if (table.text().contains("Lab Configuration")) {
				String labConfig = DataAnalyseForCloudLab.getTrTdDataFromTable(table, 2, 2);
				if (labConfig.toLowerCase().contains("nms")) {
					labType = 2;
				}
				netactLab.setLabConfig(labConfig);
				netactLab.setLabType(labType);
				continue;
			} else if (table.text().contains("Networking Details")) {
				Elements trs = table.getElementsByTag("tr");
				for (int i = 2; i < trs.size(); i++) {
					Element tr = trs.get(i);
					Element tempTable = tr.getElementsByTag("td").get(3);
					String trText = tr.text().toLowerCase();
					if ((trText.contains("node01") && trText.contains("vm-1")) || (trText.contains("node02") && trText.contains("vm-4"))) {
						String dbIpv4 = DataAnalyseForCloudLab.getTrTdDataFromTable(tempTable, 1, 4);
						netactLab.setDbIpv4(dbIpv4);
						continue;
					} else if (trText.contains("viis")) {
						String viisIpv4 = DataAnalyseForCloudLab.getTrTdDataFromTable(tempTable, 1, 4);
						netactLab.setViisIpv4(viisIpv4);
						continue;
					} else if (trText.contains("vda-1") && labType == 2) {
						String vda1Ipv4 = DataAnalyseForCloudLab.getTrTdDataFromTable(tempTable, 1, 4);
						netactLab.setVda1Ipv4(vda1Ipv4);
					}
				}
				continue;
			} else if (table.text().contains("Package")) {
				String lbwasIpv4 = DataAnalyseForCloudLab.getTrTdDataFromTable(table, 2, 2);
				netactLab.setLbwasIpv4(lbwasIpv4);
			}
		}
		return netactLab;
	}


	private Date dateFormat(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date newDate = null;
		try {
			newDate = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newDate;
	}

	public static void main(String[] args) throws IOException {
		CrawlerForCloudLab crawler = new CrawlerForCloudLab();
		NetactLab lab = crawler.getLabDetail("clab261");
		System.out.println(lab);
	}

}
