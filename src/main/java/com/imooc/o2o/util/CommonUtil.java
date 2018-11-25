package com.imooc.o2o.util;

import java.io.File;

public class CommonUtil {

	/**
	 * 获取文件编码类型
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileName(File file) {
		String fileCode = (String) System.getProperties().get("file.encoding");
		// logger.debug("fileCode:" + fileCode);

		return fileCode;
	}

}
