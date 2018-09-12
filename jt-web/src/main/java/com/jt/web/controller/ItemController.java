package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/{itemId}",method=RequestMethod.GET)
public String findItemById(@PathVariable(value="itemId") Long itemId,Model model){
		//根据商品Id查询商品信息和商品详情信息
		Item item=itemService.findItemById(itemId);
		ItemDesc itemDesc=itemService.findItemDescById(itemId);
		model.addAttribute("item", item);
		//跳转到商品展现页面
		return "item";
	}
}
