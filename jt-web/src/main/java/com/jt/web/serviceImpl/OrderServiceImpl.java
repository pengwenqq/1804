package com.jt.web.serviceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Order;
import com.jt.web.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private HttpClientService httpClientService;
	private ObjectMapper objectMapper=new ObjectMapper();
	@Override
	public String saveOrder(Order order) {
		String url = "http://order.jt.com/order/create";
		String orderId=null;
		try {
			String orderJson = objectMapper.writeValueAsString(order);
			Map<String, String > params=new HashMap<>();
			params.put("orderJson", orderJson);
			orderId = httpClientService.post(url, params);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("订单入库成功");
		return orderId;
	}
	@Override
	public Order findOrderById(Long orderId) {
		String url="http://order.jt.com/order/query/"+orderId;
		System.out.println(orderId);
		String orderJson = httpClientService.get(url);
		Order order=null;
		try {
		order = objectMapper.readValue(orderJson, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return order;
	}

}
