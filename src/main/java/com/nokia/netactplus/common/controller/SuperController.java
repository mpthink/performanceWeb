package com.nokia.netactplus.common.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.TypeUtils;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.mybatisplus.plugins.Page;

/**
 * 基础控制器
 * @author mpthink
 *
 */
public class SuperController {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	@Autowired
	protected HttpSession session;

	@Autowired
	protected ServletContext application;



	/**
	 * 用户ID
	 */
	protected Long getCurrentUserId() {
		return getSSOToken().getId();
	}


	/**
	 * 返回登录 Token
	 */
	protected SSOToken getSSOToken() {
		SSOToken tk = SSOHelper.attrToken(request);
		if (tk == null) {
			throw new RuntimeException("-1,The user does not exist, please relogin.");
		}
		return tk;
	}


	/**
	 * 是否为 post 请求
	 */
	protected boolean isPost() {
		return HttpUtil.isPost(request);
	}


	/**
	 * 是否为 get 请求
	 */
	protected boolean isGet() {
		return HttpUtil.isGet(request);
	}

	/**
	 * <p>
	 * 获取分页对象
	 * </p>
	 */
	protected <T> Page<T> getPage(int pageNumber) {
		return getPage(pageNumber, 15);
	}


	/**
	 * <p>
	 * 获取分页对象
	 * </p>
	 *
	 * @param size
	 *            每页显示数量
	 * @return
	 */
	protected <T> Page<T> getPage(int pageNumber, int pageSize) {
		return new Page<T>(pageNumber, pageSize);
	}


	/**
	 * <p>
	 * 获取分页对象  bootstrap table 的时候需要用到这个分页
	 * </p>
	*/
	protected <T> Page<T> getPageForBootstrapTable() {
		return getPageForBootstrapTable(10);
	}



	/**
	 * <p>
	 * 获取分页对象
	 * </p>
	 *
	 * @param size
	 *            每页显示数量
	 * @return
	 */

	protected <T> Page<T> getPageForBootstrapTable(int size) {
		int _size = size, _index = 1;
		if (request.getParameter("_size") != null) {
			_size = Integer.parseInt(request.getParameter("_size"));
		}
		if (request.getParameter("_index") != null) {
			int _offset = Integer.parseInt(request.getParameter("_index"));
			_index = _offset / _size + 1;
		}
		return new Page<T>(_index, _size);
	}


	/**
	 * 重定向至地址 url
	 *
	 * @param url
	 *            请求地址
	 * @return
	 */
	protected String redirectTo(String url) {
		StringBuffer rto = new StringBuffer("redirect:");
		rto.append(url);
		return rto.toString();
	}

	/**
	 * <p>
	 * 转换为 bootstrap-table 需要的分页格式 JSON
	 * </p>
	 *
	 * @param page
	 *            分页对象
	 * @return
	 */
	protected String jsonPage(Page<?> page) {
		JSONObject jo = new JSONObject();
		jo.put("total", page.getTotal());
		jo.put("rows", page.getRecords());
		return toJson(jo);
	}


	/**
	 *
	 * 返回 JSON 格式对象
	 *
	 * @param object
	 *            转换对象
	 * @return
	 */
	protected String toJson(Object object) {
		TypeUtils.compatibleWithJavaBean = true;
		return JSON.toJSONString(object, SerializerFeature.BrowserCompatible);
	}
	
	
	/**
	 *
	 * 返回 JSON 格式对象,允许NullValue in map
	 *
	 * @param object
	 *            转换对象
	 * @return
	 */
	protected String toJsonWithMapNullVal (Object object) {
		TypeUtils.compatibleWithJavaBean = true;
		return JSON.toJSONString(object,SerializerFeature.BrowserCompatible,SerializerFeature.WriteMapNullValue);
	}


	/**
	 *
	 * 返回 JSON 格式对象
	 *
	 * @param object
	 *            转换对象
	 * @param features
	 *            序列化特点
	 * @return
	 */
	protected String toJson(Object object, String format) {
		if (format == null) {
			return toJson(object);
		}
		TypeUtils.compatibleWithJavaBean = true;
		return JSON.toJSONStringWithDateFormat(object, format, SerializerFeature.WriteDateUseDateFormat);
	}

}
