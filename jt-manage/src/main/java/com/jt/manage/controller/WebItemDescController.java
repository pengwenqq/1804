package com.jt.manage.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;

@Controller
@RequestMapping("/web/item")
public class WebItemDescController {
	@Autowired
	private ItemDescService itemDescService;
	@RequestMapping("/findItemDescById")
	@ResponseBody
	public ItemDesc findItemDescById(@Param(value="itemId") Long itemId){
	ItemDesc itemDesc=itemDescService.findItemDescById(itemId);
	return itemDesc;
}
}
