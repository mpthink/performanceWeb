package com.nokia.netactplus.db.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DBCreate {

	public static void main(String[] args) throws IOException {
		String mysqlPath = "C:\\Program Files\\MySQL\\MySQL Server 5.7\\bin\\";
		String user = "root";
		String password = "phpwind.net";
		Runtime rt = Runtime.getRuntime(); // 获取运行时系统
		File systemSqlFile = new File("src/main/resources/sql/NetActPlusAdmin.sql");
		System.out.println(systemSqlFile.getAbsoluteFile());
		String command =
			"cmd /c \"cd /d " + mysqlPath + " && mysql.exe  -u" + user + " -p" + password + " < " + systemSqlFile.getAbsolutePath() + " \"";
		System.out.println(command);
		Process proc = rt.exec(command); // 执行命令
		InputStream stderr = proc.getInputStream(); // 获取输入流
		InputStreamReader isr = new InputStreamReader(stderr);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		while ((line = br.readLine()) != null) { // 打印出命令执行的结果
			System.out.println(line);
		}

		File quartzSqlFile = new File("src/main/resources/sql/tables_mysql_innodb.sql");
		String command2 =
			"cmd /c \"cd /d " + mysqlPath + " && mysql.exe  -u" + user + " -p" + password + " netactplusadmin < " + quartzSqlFile.getAbsolutePath()
				+ " \"";

		Process proc2 = rt.exec(command2); // 执行命令
		InputStream stderr2 = proc2.getInputStream(); // 获取输入流
		InputStreamReader isr2 = new InputStreamReader(stderr2);
		BufferedReader br2 = new BufferedReader(isr2);
		String line2 = null;
		while ((line2 = br2.readLine()) != null) { // 打印出命令执行的结果
			System.out.println(line2);
		}

	}

}
