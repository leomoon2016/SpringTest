package com.imooc.o2o.service;

import java.io.InputStream;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

public interface ShopService {

	/**
	 * 创建商铺
	 * 
	 * @param Shop shop
	 * @return ShopExecution shopExecution
	 * @throws Exception
	 */
	ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

	/**
	 * 更新店铺
	 */
	ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

	/**
	 * 获取店铺
	 */
	Shop getByShopId(long shopId);

	/**
	 * 获取店铺列表
	 * 
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
}
