package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();

	static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	public static String generateThumbnail(InputStream thumbnailInputStream, String fileName, String targetAddr) {

		String realFileName = getRandomFileName();
		String extension = getFileExtension(fileName);
		makeDirPath(targetAddr);
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("crrent relativeAddr:" + relativeAddr);
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("dest name:" + PathUtil.getImgBasePath() + relativeAddr);

		try {
			logger.debug("water IMG:" + basePath + "watermark.jpg");

			Thumbnails.of(thumbnailInputStream).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "watermark.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return relativeAddr;

	}

	/**
	 * 随机文件名
	 */
	public static String getRandomFileName() {
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

	/**
	 * 获取扩展名get
	 */
	private static String getFileExtension(String fileName) {

		return fileName.substring(fileName.lastIndexOf("."));
	}

	/**
	 * 创建目标路径所涉及的目录
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 文件删除
	 */
	public static void deleteFileOrOath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		logger.debug("删除的地址：" + fileOrPath);
		if (fileOrPath.exists()) {
			logger.debug("存在");
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}

	}

}
