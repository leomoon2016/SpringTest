package com.imooc.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private ShopDao shopDao;
	Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
		// 空值判断
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
				// throw new ShopOperationException("店铺创建失败");
			} else {

				if (shopImgInputStream != null) {
					logger.debug("file name:" + fileName);

					try {
						addShopImg(shop, shopImgInputStream, fileName);

					} catch (Exception e) {
						throw new ShopOperationException("addShopImg：" + e.getMessage());
					}
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址 error！");
					}
				}
			}

		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {

		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {

		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {

		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		} else {
			try {
				// 1、判断是否要处理图片
				if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {

					Shop shopTemp = shopDao.queryByShopId(shop.getShopId());
					logger.debug("查询到的图片地址：" + shopTemp.getShopImg());
					if (shopTemp.getShopImg() != null) {
						ImageUtil.deleteFileOrOath(shopTemp.getShopImg());
					}
					addShopImg(shop, shopImgInputStream, fileName);

				}
				// 2、更新店铺信息
				shop.setLastEditTime(new Date());

				int effectNum = shopDao.updateShop(shop);
				if (effectNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);

				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);

				}
			} catch (Exception e) {
				throw new ShopOperationException("modify shop error:" + e.getMessage());
			}
		}

	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);

		ShopExecution se = new ShopExecution();

		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);

		} else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}

		return se;
	}

}
