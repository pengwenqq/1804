package com.jt.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
@Autowired
	private ItemCatService itemCatService;

/**
 * 为了实现EasyUi的树形结构,传递id值
 * (@RequestParam(defaultValue="".id不传时的默认值
 * required="",是否一定要有id参数传递
 * value=""，传递参数的key值)
 * 
 * @return
 */
	@RequestMapping("/list")
	@ResponseBody
public  List<EasyUITree> findItemCatList(@RequestParam(defaultValue="0",value="id") Long parentId){
		//查询一级商品分类目录
		List<EasyUITree> treeList=itemCatService.findItemCatList(parentId);
		return treeList;
}
}
