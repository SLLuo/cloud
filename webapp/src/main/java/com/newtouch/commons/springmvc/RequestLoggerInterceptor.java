package com.newtouch.commons.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestLoggerInterceptor extends HandlerInterceptorAdapter {

	public static final Logger FILTER_LOG = LoggerFactory.getLogger(RequestLoggerInterceptor.class);
	
	private String[] ignoreArr = new String[0];
	//ignores
	public void setIgnores(String ignores) {
		if (StringUtils.isNotEmpty(ignores)) {
			this.ignoreArr = ignores.split(",");
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		String path = request.getPathInfo();

		for (String ignore : ignoreArr) {
			if (path.startsWith(ignore)) {
				return;
			}
		}

		String addr = request.getRemoteAddr();
		String base = request.getContextPath();
		String query = request.getQueryString();
		// 页面从哪里链接过来
		String referer = request.getHeader("referer"); // http://localhost:8080/portal/login_view 如果是登录跳转
		//根据报文 判断是否是ajax请求
		//值为XMLHttpRequest,表示客户端的请求为异步请求，如果为null,则是普通的请求
		String requestType = request.getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equals(requestType);
	}
	
}