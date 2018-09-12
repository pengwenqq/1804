package com.jt.manage.service;

import com.jt.manage.pojo.ItemDesc;

public interface ItemDescService {

	ItemDesc findItemDesc(Long itemId);

	ItemDesc findItemDescById(Long itemId);

}
