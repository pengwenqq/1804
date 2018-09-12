package com.jt.manage.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;
@Service
public class ItemDescServiceImpl implements ItemDescService {
	@Autowired
private ItemDescMapper itemDescMapper;
	@Override
	public ItemDesc findItemDesc(Long itemId) {
		ItemDesc desc = itemDescMapper.selectByPrimaryKey(itemId);
		return desc;
	}
	@Override
	public ItemDesc findItemDescById(Long itemId) {
		ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);
		return itemDesc;
	}
}
