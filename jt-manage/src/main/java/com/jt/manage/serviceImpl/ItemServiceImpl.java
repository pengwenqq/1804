package com.jt.manage.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private ItemDescMapper itemDescMapper;
 
	@Override
	public EasyUIResult findItemByPage(Integer page, Integer rows) {
		//int total=itemMapper.findItemCount();		
		int total=itemMapper.selectCount(null);
		//商品分页后的List集合
		/**
		 * select * from tb_item limit 起始位置，每页的行数
		 */
		int startSize=(page-1)*rows;
		List<Item> itemList=itemMapper.findItemListByPage(startSize,rows);
		
		return new EasyUIResult(total, itemList);
	}

	@Override
	public String findItemCatNameById(Long itemId) {
		//这个mapper查询时借用的应该用itemCatMapper
		String name=itemMapper.findItemCatNameById(itemId);
		return name;
	}

	//封装Item数据
	@Override
	public void saveItem(Item item,String desc) {
		item.setStatus(1);//1.表示上架正常
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void updateItem(Item item,String desc) {
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setUpdated(item.getUpdated());
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}
	

	@Override
	public void deleteItems(Long[] ids) {
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIDS(ids);
	}

	@Override
	public void updateState(Long[] ids, int status) {
		itemMapper.updateState(ids,status);
		
	}

	@Override
	public Item findItemById(Long itemId) {
		Item item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}
}
