package com.jt.manage.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/web/item")
public class WebItemController {
	@Autowired
	private ItemService itemService;
	@RequestMapping("/findItemById")
	@ResponseBody
	public Item findItemById(@Param(value="itemId") Long itemId){
		Item item=itemService.findItemById(itemId);
		return item;
	}

}
