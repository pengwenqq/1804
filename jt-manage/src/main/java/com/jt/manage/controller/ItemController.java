package com.jt.manage.controller;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {
	//为项目添加日志
	private static final Logger logger=Logger.getLogger(ItemController.class);
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping("/query")
	@ResponseBody //将对象转换为json串
	public EasyUIResult findItemByPage(Integer page, Integer rows) {
		return itemService.findItemByPage(page,rows);
	}
	/**
	 * 查找叶子目录
	 * @ResponseBody 解析说明
	 * 如果解析的是对象 则默认使用UTF-8编码格式
	 * 如果解析是字符串，则默认使用iso-8850-1编码
	 * @return
	 */
	@RequestMapping(value="/cat/queryItemName",produces="text/html; charset=UTF-8")
	@ResponseBody
	public String findItemCatNameById(Long itemId){
		return itemService.findItemCatNameById(itemId);
	}
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品新增失败");
	}
	
	
	
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			//System.out.println("记录程序执行状态");
			logger.info("!!!!!!!!!记住状态");
			return SysResult.oK();
		} catch (Exception e) {
			logger.error("~~~~~~~~~~~"+e.getMessage());
			e.printStackTrace();
}
		return SysResult.build(201, "修改商品失败");
	}
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(Long[] ids){
		try {
			itemService.deleteItems(ids);
			logger.info("!!!!!!!!!记住状态");
			return SysResult.oK();
		} catch (Exception e) {
			logger.error("~~~~~~~~~~~"+e.getMessage());
		}
		return SysResult.build(201, "删除商品失败");
	}
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelfItems(Long[] ids){
		try {
			int status=1;
			itemService.updateState(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品下架失败");
	}
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instockItems(Long[] ids){
		try {
			int status=2;
			itemService.updateState(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品上架失败");
	}
	/**
	 * 商品详情回显
	 * @param itemId
	 * @return
	 */
	//item/query/item/desc/1474391968
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDesc(@PathVariable("itemId") Long itemId){
		try {
		ItemDesc desc= itemDescService.findItemDesc(itemId);
		return new SysResult(desc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new SysResult("商品查询失败");
	}
	
}
