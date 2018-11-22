package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {

	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();

	// Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	public static String generateThumbnail(File thumbnail, String targetAddr) {
		Logger logger = LoggerFactory.getLogger(ImageUtil.class);
		// 随机名
		String realFileName = getRandomFileName();
		// 扩展名
		String extension = getFileExtension(thumbnail);
		// 生成路径
		makeDirPath(targetAddr);
		// 相对路径
		String relativeAddr = targetAddr + realFileName + extension;

		logger.debug("relativeAddr：" + relativeAddr);
		// 新文件
		File dest = new File(PathUtil.getImageBasePath() + relativeAddr);

		logger.debug("completeAddr：" + PathUtil.getImageBasePath() + relativeAddr);

		try {
			Thumbnails.of(((MultipartFile) thumbnail).getInputStream()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return relativeAddr;

	}

	/**
	 * 创建目标路径所涉及的目录
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImageBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}

	/**
	 * 获取扩展名get
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(File cFile) {
		String originalFileName = ((MultipartFile) cFile).getOriginalFilename();
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}

	/**
	 * 随机文件名
	 * 
	 * @return
	 */
	private static String getRandomFileName() {
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}

//	public static void main(String[] args) throws IOException {
//
//		Thumbnails.of(new File("D:\\\\TMP\\1024.jpg")).size(800, 800)
//				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
//				.outputQuality(0.8f).toFile("D:\\\\TMP\\1024_1.jpg");
//
//	}

}
