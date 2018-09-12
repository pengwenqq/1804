package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.service.RedisService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisService redisService;
	@RequestMapping("/{moduleName}")
	public String module(@PathVariable("moduleName") String moduleName) {
		return moduleName;
	}

	// 实现用户的注册
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户注册失败");
	}

	// 实现用户的登录
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(User user, HttpServletResponse response) {
		try {
			// 获取加密后的秘钥
			String tonken = userService.findUserByUP(user);
			// 判断token是否为空
			if (StringUtils.isEmpty(tonken)) {
				throw new RuntimeException();
			}
			// 将token写到客户端的cookie中 4k
			Cookie cookie = new Cookie("JT_TICKET", tonken);
			cookie.setPath("/");// 保存到根目录
			cookie.setMaxAge(7 * 24 * 3600);// 最大生命周期 单位秒
			response.addCookie(cookie);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "登录失败");
	}
	//用户登出时删除：1.redis缓存 2.删除Cookie
		/**
		 *1.获取cookie数据
		 *2.删除redis缓存
		 *3.将Cookie删除
		 * @return
		 */
		@RequestMapping("/logout")
		public String logout(HttpServletRequest request,HttpServletResponse respon){
			Cookie[] cookies = request.getCookies();
			String tooken=null;
			for (Cookie cookie : cookies) {
				if("JT_TICKET".equals(cookie.getName())){
					tooken=cookie.getValue();
					break;
				}
			}
			redisService.jedisClusterDel(tooken);
			Cookie cookie = new Cookie("JT_TICKET", "");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			respon.addCookie(cookie);
			
			return "redirect:/index.html";
		}

}
