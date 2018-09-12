package com.jt.web.intercepter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.web.pojo.User;
import com.jt.web.thread.UserThreadLocal;

//Handler 执行controller  service mapper 的东西
public class UserIntercepter implements HandlerInterceptor {
	@Autowired
	private RedisService redisService;
	private static ObjectMapper objectMapper=new ObjectMapper();

	// 在执行controller之前执行
	/**
	 * boolean 表示是否放行 true:放行，跳转页面 false:拦截 之后给定重定向的路径
	 * 
	 * 业务逻辑： 1.判断用户客户端是否有cookie/token数据 如果没有token则重定向到用户的登录页面
	 * 2.如果用户token中有数据，则从redis缓存数据中获取数据 如果redis中数据位null，则重定向到用户的登录页面
	 * 3.如果redis中有数据,则放行请求
	 */

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取Cookie数据
		Cookie[] cookies = request.getCookies();
		String token = null;
		for (Cookie cookie : cookies) {
			if ("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();
				break;
			}
		}
		// 判断cookie是否为空
		if (!StringUtils.isEmpty(token)) {
			// 查看缓存中是否有该数据
			String userJson = redisService.jedisClusterGet(token);
			if (!StringUtils.isEmpty(userJson)) {
				// 用户已经登录
				// 将userJson转化为User对象
				User user = objectMapper.readValue(userJson, User.class);
				// 思路
				UserThreadLocal.set(user);
				return true;
			}
		}
		// 表示用户没有登录
		response.sendRedirect("/user/login.html");
		return false;
	}

	// 在执行完业务逻辑之后执行
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	// 返回页面之前拦截
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//将ThreadLocal清空
		UserThreadLocal.remove();
	}

}
