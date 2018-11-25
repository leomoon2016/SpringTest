package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;

public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;
	Logger logger = LoggerFactory.getLogger(ShopDaoTest.class);

	@Test
	public void testQueryShopList() {
		Shop shopCondition = new Shop();
		
		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);
		
		ShopCategory shopCategory=new ShopCategory();
		shopCategory.setShopCategoryId(1L);
		
		shopCondition.setShopCategory(shopCategory);
		shopCondition.setOwner(owner);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 5);
		System.out.println("行数：" + shopList.size());
		int count = shopDao.queryShopCount(shopCondition);
		System.out.println("总数：" + count);

	}

	@Test
	@Ignore
	public void testQueryByShopId() {
		long shopId = 1;
		Shop shop = shopDao.queryByShopId(shopId);
		System.out.println(shop.getArea().getAreaId());
		System.out.println(shop.getArea().getAreaName());
	}

	@Test
	@Ignore
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
