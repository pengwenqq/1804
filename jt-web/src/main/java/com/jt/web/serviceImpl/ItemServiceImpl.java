package com.jt.web.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private HttpClientService httpClientService;
	private static ObjectMapper objectMapper=new ObjectMapper();

	@Override
	public Item findItemById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemById";
		Map<String, String> params = new HashMap<>();
		params.put("itemId", itemId.toString());
		Item item=null;
		String data = httpClientService.get(url, params);
		try {
			item = objectMapper.readValue(data, Item.class);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public ItemDesc findItemDescById(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById";
		Map<String, String> params = new HashMap<>();
		params.put("itemId", itemId.toString());
		ItemDesc itemDesc=null;
		String data = httpClientService.get(url, params);
		try {
			itemDesc = objectMapper.readValue(data, ItemDesc.class);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}
