package com.imooc.o2o.service.impl;

import java.io.File;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.enerty.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, File shopImg) {
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 店铺赋值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());

			// 添加一条信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				logger.debug("shopImg:" + shopImg.toString());
				if (shopImg != null) {
					;
					/*
					 * logger.debug("shopImg not null:" + (shopImg != null)); // 存储图片 try {
					 * logger.debug("-------addShopImg start not in-----"); addShopImg(shop,
					 * shopImg); logger.debug("-------addShopImg end not in-----"); } catch
					 * (Exception e) { logger.error("shopImg error:" + e.getMessage()); throw new
					 * ShopOperationException("addShopImg error:" + e.getMessage()); } // 更新图片地址
					 * effectedNum = shopDao.updateShop(shop);
					 * 
					 * if (effectedNum <= 0) { throw new ShopOperationException("更新图片地址 error！"); }
					 */
				}
			}

		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, File shopImg) {
		logger.debug("-------PathUtil getShopImagePath  in-----:" + shop.getShopId());
		// 获取shop图片目录的相对值路径
		String dest = PathUtil.getShopImagePath(shop.getShopId());
		logger.debug("dest:" + dest);
		String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
		logger.debug("shopImgAddr:" + shopImgAddr);
		shop.setShopImg(shopImgAddr);

	}

}
