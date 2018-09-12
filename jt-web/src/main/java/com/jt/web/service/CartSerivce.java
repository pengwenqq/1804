package com.jt.web.service;

import java.util.List;

import com.jt.web.pojo.Cart;

public interface CartSerivce {

	List<Cart> findCartListByUserId(Long userId);

	void saveCart(Cart cart);

	void updateCartNum(Cart cart);

	void deleteCartNum(Cart cart);

}
