package com.imooc.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.imooc.o2o.entity.Shop;

public interface ShopDao {

	/**
	 * 分页查询
	 * 
	 * @param rowIndex 从第几行
	 * @param pageSize 返回的条数
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	/**
	 * 查询返回的总数
	 * 
	 * @param shopCondtion
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);

	/**
	 * 查询SHOP
	 */
	Shop queryByShopId(long shopId);

	/**
	 * 新增
	 * 
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);

	/**
	 * 更新
	 * 
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);

}
