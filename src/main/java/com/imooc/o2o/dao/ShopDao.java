package com.imooc.o2o.dao;

import com.imooc.o2o.enerty.Shop;

public interface ShopDao {
	/**
	 * 新增
	 * 
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	
	/**
	 * 更新
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);

}
