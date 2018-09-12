package com.jt.web.controller;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.druid.pool.DruidDataSource;

@Controller
public class IndexController {
	@RequestMapping("/index")
	public String index(){
		System.out.println("进入页面跳转");
	return "index";
}
}
