package com.dell.petshow.common;

import java.io.IOException;

import com.dell.petshow.common.ssh.SshBasic;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class Test {

	public static void main(String[] args) throws IOException {
		SshBasic sshBasic = new SshBasic();
		String hostname = "10.207.80.60";
		int port = 22;
		String username = "root";
		String password = "Password123!";
		Connection connection = sshBasic.login(hostname, port, username, password);
		String command = "/usr/bin/python /space/vtas/fio-Linux.py 10.141.52.217  root  Password123!";
		Session session = connection.openSession();

		sshBasic.exeCmd(session, command);
		System.out.println("ok");

	}

}
