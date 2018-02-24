package com.dell.petshow.vtas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.dell.petshow.common.controller.SuperController;
import com.dell.petshow.common.dto.NPResult;
import com.dell.petshow.common.ssh.SshBasic;
import com.dell.petshow.vtas.entity.HostInformation;
import com.dell.petshow.vtas.service.IHostInformationService;
import com.dell.petshow.vtas.service.IOfficialSwVersionsService;

import ch.ethz.ssh2.Connection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mpthink
 * @since 2018-01-26
 */
@Controller
@RequestMapping("/vtas/hostInformation")
public class HostInformationController extends SuperController {

	@Autowired
	private IHostInformationService hostInformationService;
	@Autowired
	private IOfficialSwVersionsService officialSwVersionsService;
	@Autowired
	private SshBasic sshBasic;

	@RequestMapping("/list")
	public String list(Model model) {
		return "vtas/arrayConfiguration/hostconfiglist";
	}


	@RequestMapping("/getOfficalSWVersions")
	@ResponseBody
	public String getOfficalSWVersions() {
		return toJson(officialSwVersionsService.selectList(null));
	}

	@RequestMapping("/getHostInfos")
	@ResponseBody
	public String getHostInfos() {
		Wrapper<HostInformation> wrapper = new EntityWrapper<>();
		wrapper.orderBy("ARRAY_NAME");
		return toJson(hostInformationService.selectList(null));
	}

	@RequestMapping("/executeSSHcommand")
	@ResponseBody
	public NPResult executeSSHcommand(@RequestParam("command") String command) {

		String hostname = "10.207.80.60";
		int port = 22;
		String username = "root";
		String password = "Password123!";
		Connection connection = sshBasic.login(hostname, port, username, password);
		boolean result = sshBasic.exeCmd(connection, command);
		if (result) {
			return new NPResult().success();
		} else {
			return new NPResult().failure();
		}

	}

}
