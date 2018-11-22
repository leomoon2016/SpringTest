package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.enerty.Area;
import com.imooc.o2o.enerty.PersonInfo;
import com.imooc.o2o.enerty.Shop;
import com.imooc.o2o.enerty.ShopCategory;

public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;
	Logger logger = LoggerFactory.getLogger(ShopDaoTest.class);

	@Test
	// @Ignore
	public void testInsertShop() {

		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		Area area = new Area();
		ShopCategory shopCategory = new ShopCategory();

		owner.setUserId(1L);
		area.setAreaId(1);
		shopCategory.setShopCategoryId(1L);

		shop.setArea(area);
		shop.setOwner(owner);
		shop.setShopCategory(shopCategory);

		shop.setShopName("测试名称");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setShopImg("test");
		shop.setPriority(1);
		shop.setPhone("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");

		try {

			int effectedNum = shopDao.insertShop(shop);
			assertEquals(1, effectedNum);

		} catch (Exception e) {

			e.printStackTrace();
			logger.error(e.toString());
		}

	}

	@Test
	@Ignore
	public void updateShop() {

		Shop shop = new Shop();

		shop.setShopDesc("测试描述");
		shop.setShopAddr("测试地址");
		shop.setLastEditTime(new Date());

		try {
			int effectedNum = shopDao.updateShop(shop);
			assertEquals(1, effectedNum);

		} catch (Exception e) {

			e.printStackTrace();
			logger.error(e.toString());
		}

	}

}
