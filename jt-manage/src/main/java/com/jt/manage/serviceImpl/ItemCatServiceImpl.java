package com.jt.manage.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.vo.EasyUITree;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public List<EasyUITree> findItemCatList(Long parentId) {
		List<EasyUITree> treeList = new ArrayList<>();
		List<ItemCat> itemCatList = findItemCatCache(parentId);
		for (ItemCat itemcatTemp : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemcatTemp.getId());
			easyUITree.setText(itemcatTemp.getName());
			// 如果是父级则close,如果不是父级则open/lk
			String state = itemcatTemp.getIsParent() ? "closed" : "open";
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}

	/**
	 * 通过parentId查询itemCats数据 1.先查询缓存
	 * 
	 * @param parentId
	 * @return
	 */
	private static final ObjectMapper objectMapper = new ObjectMapper();
	@Autowired
	private RedisService redisService;

	public List<ItemCat> findItemCatCache(Long parentId) {
		List<ItemCat> itemCats = new ArrayList<ItemCat>();
		String key = "ITEM_CAT" + parentId;

		String jsonData = redisService.jedisClusterGet(key);

		try {
			if (StringUtils.isEmpty(jsonData)) {
				// 缓存中没有数据,则查询数据库
				ItemCat itemCat = new ItemCat();
				itemCat.setParentId(parentId);
				itemCats = itemCatMapper.select(itemCat);

				// 将查询的结果先转化为JSON后保存redis中
				String itemCatJSON = objectMapper.writeValueAsString(itemCats);
				redisService.jedisClusterSet(key, itemCatJSON);
				System.out.println("第一次缓存成功");
			} else {
				// 表示缓存中有数据,将ItemCatListJSON转化List<ItemCat对象>
				// [{},{},{},{}]
				ItemCat[] arrayitemCats = objectMapper.readValue(jsonData, ItemCat[].class);
				itemCats = Arrays.asList(arrayitemCats);
				System.out.println("从缓存中取");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemCats;
	}

	/**
	 * 查询全部商品分类信息 思路： 1.parent_id=0 查询全部一级商品分类信息 2.循环遍历1级商品分类信息
	 * parent_id=一级id查询二级商品分类信息 3.遍历2级商品分类信息，parent_id=二级id 获取三级商品分类信息 问题：
	 * 查询一次商品分类信息需要连接数据，性能影响并发 设计： 1.查询全部数据库信息 2.使用Map<parentId,List
	 * <ItemCat> 子集菜单> 将数据库中查询的结果封装为Map集合， 3.Map.getKey(0)全部一级
	 * Map.getKey(一级Id)全部二级 Map.getKey(二级Id)全部三级
	 */
	@Override
	public ItemCatResult findItemCatAll() {
		ItemCat itemCatTemp = new ItemCat();
		itemCatTemp.setStatus(1);// 正常商品分类信息
		List<ItemCat> itemCatListTemp = itemCatMapper.select(itemCatTemp);
		Map<Long, List<ItemCat>> map = new HashMap<>();
		// 循环遍历数据，实现Map集合封装
		for (ItemCat itemCat : itemCatListTemp) {
			if (map.containsKey(itemCat.getParentId())) {
				map.get(itemCat.getParentId()).add(itemCat);
			} else {
				List<ItemCat> childItemCatList = new ArrayList<>();
				childItemCatList.add(itemCat);
				map.put(itemCat.getParentId(), childItemCatList);
			}
		}
		// 实现三级商品分类信息的回显
		ItemCatResult itemCatResult = new ItemCatResult();
		// 获取一级商品分类信息
		List<ItemCatData> itemCatDateList1 = new ArrayList<>();
		// 获取一级商品分类的数据
		for (ItemCat itemCat1 : map.get(0L)) {
			ItemCatData itemCatData1 = new ItemCatData();
			itemCatData1.setUrl("/products/" + itemCat1.getId() + ".html");
			itemCatData1.setName("<a href='" + itemCatData1.getUrl() + "'>" + itemCat1.getName() + "</a>");
			// 准备二级商品分类信息
			List<ItemCatData> itemCatDataList2 = new ArrayList<>();
			for (ItemCat itemCat2 : map.get(itemCat1.getId())) {
				ItemCatData itemCatData2 = new ItemCatData();
				itemCatData2.setUrl("/products/" + itemCat2.getId());
				itemCatData2.setName(itemCat2.getName());
				// itemCatData2.setItems(items);
				// 获取三级商品分类信息
				List<String> itemCatData3 = new ArrayList<>();
				for (ItemCat itemCat3 : map.get(itemCat2.getId())) {
					itemCatData3.add("/products/" + itemCat3.getId() + "|" + itemCat3.getName());
				}
				itemCatData2.setItems(itemCatData3);
				itemCatDataList2.add(itemCatData2);
			}
			itemCatData1.setItems(itemCatDataList2);

			itemCatDateList1.add(itemCatData1);
			if (itemCatDateList1.size() > 13) {
				break;
			}
		}

		itemCatResult.setItemCats(itemCatDateList1);
		return itemCatResult;
	}
public ItemCatResult findItemCatAllCache(){
	String key="ITEM_CAT_ALL";
	String result = redisService.jedisClusterGet(key);
	ItemCatResult itemCatResult=null;
	if(StringUtils.isEmpty(result)){
		itemCatResult= findItemCatAll();
		String jsonData;
		try {
			jsonData = objectMapper.writeValueAsString(itemCatResult);
			redisService.jedisClusterSet(key, jsonData);
			System.out.println("第一次缓存");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}else {
		try {
			itemCatResult = objectMapper.readValue(result, ItemCatResult.class);
			System.out.println("查询缓存");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	return itemCatResult;
}
}
