package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.enerty.Area;
import com.imooc.o2o.enerty.PersonInfo;
import com.imooc.o2o.enerty.Shop;
import com.imooc.o2o.enerty.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testAddShop() throws FileNotFoundException {

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

		shop.setShopName("测试名称1");
		shop.setShopDesc("test1");
		shop.setShopAddr("test1");
		// shop.setShopImg("test1");
		shop.setPriority(1);
		shop.setPhone("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");

		File shopImg = new File("");

		InputStream is = new FileInputStream(shopImg);

		ShopExecution se = shopService.addShop(shop, is, shopImg.getName());

		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());

	}

}
