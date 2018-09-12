package com.jt.web.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartSerivce;

@Service
public class CartServiceImpl implements CartSerivce {
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public List<Cart> findCartListByUserId(Long userId) {
		String url = "http://cart.jt.com/cart/query/" + userId;
		String JasonData = httpClient.get(url);
		List<Cart> cartList = new ArrayList<>();
		try {
			SysResult sysResult = objectMapper.readValue(JasonData, SysResult.class);
			// LinkedHashMap
			cartList = (List<Cart>) sysResult.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cartList;
	}

	@Override
	public void saveCart(Cart cart) {
		String url = "http://cart.jt.com/cart/save";
		Map<String, String> params = new HashMap<>();
		try {
			String cartJson = objectMapper.writeValueAsString(cart);
			params.put("cartJson", cartJson);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		httpClient.post(url, params);
		System.out.println("购物车入库成功");
	}

	@Override
	public void updateCartNum(Cart cart) {
		String url = "http://cart.jt.com/cart/update/num/" + cart.getUserId() + "/" + cart.getItemId() + "/"
				+ cart.getNum();
		httpClient.get(url);
		System.out.println("商品数量更新成功");
	}

	@Override
	public void deleteCartNum(Cart cart) {
		String url = "http://cart.jt.com/cart/delete/" + cart.getUserId() + "/" + cart.getItemId();
		String string = httpClient.get(url);
		System.out.println(string);
		System.out.println("商品删除成功");
	}
}
