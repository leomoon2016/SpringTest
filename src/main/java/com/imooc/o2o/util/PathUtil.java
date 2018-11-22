package com.imooc.o2o.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.imooc.o2o.service.impl.ShopServiceImpl;

public class PathUtil {

	private static String seperator = System.getProperty("file.separator");
	static Logger logger = LoggerFactory.getLogger(ShopServiceImpl.class);

	public static String getImageBasePath() {

		String os = System.getProperty("os.name");

		String basePath = "";
		
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/tmp/image/";
		} else {
			basePath = "/home/image/";
		}

		return basePath.replace("/", seperator);
	}

	public static String getShopImagePath(long shopId) {

		String imagePath = "D:/tmp/image" + shopId;

		logger.debug("imagePath:" + imagePath);

		logger.debug("seperator:" + seperator);

		return imagePath.replace("/", seperator);
	}

}
