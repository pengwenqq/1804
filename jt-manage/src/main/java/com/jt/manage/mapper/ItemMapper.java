package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends SysMapper<Item>{

	int findItemCount();

	/**
	 * 关于Mybatis中参数问题 Mybatis中不允许进行多值传参 思路：将多值转换为单值 解决： 1.使用对象封装数据 做新增、修改
	 * 2.使用map进行数据封装 多条件查询时或者参数不是pojo的属性@Param 3.array/list
	 * 
	 * @param startSize
	 * @param rows
	 * @return
	 */
	List<Item> findItemListByPage(@Param("startSize") Integer startSize, @Param("rows") Integer rows);

	@Select("select name from tb_item_cat where id = #{itemId}")
	String findItemCatNameById(Long itemId);

	void updateState(@Param("ids") Long[] ids,@Param("status") int status);

}
