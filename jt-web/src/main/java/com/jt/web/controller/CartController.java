package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.TableStat.Mode;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.service.CartSerivce;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartSerivce cartService;

	//跳转到购物车页面
	@RequestMapping("/show")
	public String findCartListByUserId(Model model){
		Long userId = UserThreadLocal.get().getId();
		//根据userId查询购物车的列表信息
		List<Cart> cartList= cartService.findCartListByUserId(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//实现购物车新增
	@RequestMapping("/add/{itemId}")
	public String saveCart(Cart cart,@PathVariable("itemId") Long itemId){
		Long userId = UserThreadLocal.get().getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService. saveCart(cart);
		
		return "redirect:/cart/show.html";
		
	}
	//实现购物车数量的修改
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCartNum(
				@PathVariable("itemId") Long itemId,
				@PathVariable("num") Integer num
			){
		try {
			Long userId = UserThreadLocal.get().getId();
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCartNum(cart);
			return SysResult.oK();
		} catch (Exception e) {
		}
return SysResult.build(201, "购物车新增失败");		
	}
	//实现购物车数量的删除
	@RequestMapping("/delete/{itemId}")
	public String deleteCartNum(@PathVariable("itemId") Long itemId){
		Long userId = UserThreadLocal.get().getId();
			Cart cart = new Cart();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cartService.deleteCartNum(cart);
		return "redirect:/cart/show.html";
	}
}
