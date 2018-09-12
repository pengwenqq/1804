package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.vo.EasyUITree;

public interface ItemCatService {

	List<EasyUITree> findItemCatList(Long parentId);
	/**
	 * 查询全部商品分类信息
	 * @return
	 */
	ItemCatResult findItemCatAll();
	ItemCatResult findItemCatAllCache();

}
