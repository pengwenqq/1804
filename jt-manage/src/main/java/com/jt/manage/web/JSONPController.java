package com.jt.manage.web;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.manage.pojo.User;

@Controller
public class JSONPController {
	
	public String JSONP(String callback) throws JsonProcessingException{
		User usr=new User();
		usr.setId(100);
		usr.setName("tomcat");
		usr.setAge(18);
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(usr);
		return callback+"("+json+")";
	}
	//使用工具类封装JSONP请求
	@RequestMapping("/web/testJSONP")
	@ResponseBody
	public MappingJacksonValue MappingJSONP(String callback) throws JsonProcessingException{
		User usr=new User();
		usr.setId(100);
		usr.setName("tomcat");
		usr.setAge(18);
		MappingJacksonValue jacksonValue = new MappingJacksonValue(usr);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
}
}
