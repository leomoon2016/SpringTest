package com.imooc.o2o.dao;

import java.util.List;

import com.imooc.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	/**
	 * 通过店铺ID 返回商品类别列表
	 * 
	 * @param shopId
	 * @return
	 */
	List<ProductCategory> queryProductCategoryList(long shopId);

}
