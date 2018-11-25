package com.imooc.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	@Ignore
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

		shop.setShopName("Junit测试名称2");
		shop.setShopDesc("test1");
		shop.setShopAddr("test1");
		// shop.setShopImg("test1");
		shop.setPriority(1);
		shop.setPhone("test");
		shop.setCreateTime(new Date());
		shop.setEnableStatus(ShopStateEnum.CHECK.getState());
		shop.setAdvice("审核中");

		File shopImg = new File("D:/tmp/1.jpg");

		InputStream is = new FileInputStream(shopImg);

		ShopExecution se = shopService.addShop(shop, is, shopImg.getName());

		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());

	}

	@Test
	@Ignore
	public void testModifyShop() throws ShopOperationException, FileNotFoundException {
		Shop shop = new Shop();
		shop.setShopId(1L);
		shop.setShopName("修改后的店铺2");
		File shopImg = new File("D:/tmp/4.jpg");
		InputStream is = new FileInputStream(shopImg);

		ShopExecution se = shopService.modifyShop(shop, is, shopImg.getName());
		System.out.println("new地址：" + se.getShop().getShopImg());
	}

	@Test
	public void testGethopList() {
		Shop shopCondition = new Shop();

		PersonInfo owner = new PersonInfo();
		owner.setUserId(1L);

		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(1L);

		shopCondition.setShopCategory(shopCategory);
		shopCondition.setOwner(owner);

		ShopExecution se = shopService.getShopList(shopCondition, 6, 2);

		System.out.println("列表数：" + se.getShopList().size());
		System.out.println("总数：" + se.getCount());

	}

}
