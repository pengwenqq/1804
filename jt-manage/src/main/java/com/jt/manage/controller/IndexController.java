package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
@RequestMapping("/index")
	public String index(){
	return "index";
}
/**
 * 通用页面跳转
 * get请求:
 * 		localhost:8091/addUser?id=1&name=tom
 * 	restFul:
 * 		localhost:8091/addUser/1/tom
 * 
 * 参数格式要求
 * 	使用{moduleName}包裹参数，如果有多个参数使用“/”分割参数位置是固定的
 * 使用@PathVariable(value="moduleName") String moduleName动态获取参数
 * url:/page/item-add
 * @return
 */
@RequestMapping("/page/{moduleName}")
public String toCreate(@PathVariable(value="moduleName") String moduleName){
	//跳转页面
	return moduleName;
}
}
