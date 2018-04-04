package com.dell.petshow.common.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

@Component
public class SshBasic {
	private final static Logger LOGGER = LoggerFactory.getLogger(SshBasic.class);

	//连接，登陆
	public Connection login(String hostname, int port, String username, String password) {

		//获取连接
		Connection conn = new Connection(hostname, port);
		try {
			//连接
			conn.connect();
			//输入账号密码登陆
			boolean isAuthenticated = conn.authenticateWithPassword(username, password);
			//登陆失败，返回错误
			if (isAuthenticated == false) {
				throw new IOException("isAuthentication failed.");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return conn;
	}

	//执行命令
	public Boolean exeCmd(Connection conn, String cmd) {
		Session sess = null;
		try {
			sess = conn.openSession();
			exeCmd(sess, cmd);
			sess.close();
			LOGGER.debug("connection info: " + conn.getConnectionInfo());
			LOGGER.info("Execute command: " + cmd + " succssfully");
		} catch (IOException e) {
			LOGGER.error("Execute command: " + cmd + " failed");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*    //执行命令  */
	public void exeCmd(Session sess, String cmd) {

		try {
			sess.execCommand(cmd);
			//打印信息
			InputStream stdout = new StreamGobbler(sess.getStdout());
			//打印错误
			InputStream stderr = new StreamGobbler(sess.getStderr());
			BufferedReader brout = new BufferedReader(new InputStreamReader(stdout, "UTF-8"));
			BufferedReader brerr = new BufferedReader(new InputStreamReader(stderr, "UTF-8"));
			LOGGER.debug("exe cmd result info: " + brout.readLine());
			LOGGER.error("exe cmd err info: " + brerr.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//获取控制台打印信息
	/*    public String printCmd(String path,Connection conn,Session sess, String date, String city){
	    String txt = "";
	    try {
	        sess.execCommand("chmod 755 "+path+" && "+path+" "+date+" "+city);
	        //打印信息
	        InputStream stdout = new StreamGobbler(sess.getStdout());
	        //打印错误
	        InputStream stderr = new StreamGobbler(sess.getStderr());
	        BufferedReader brout = new BufferedReader(new InputStreamReader(stdout,"UTF-8"));
	        BufferedReader brerr = new BufferedReader(new InputStreamReader(stderr,"UTF-8"));
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	
	    return txt;
	}*/

}
