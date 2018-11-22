package com.imooc.o2o.web.shopadmin;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.enerty.PersonInfo;
import com.imooc.o2o.enerty.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerShop(HttpServletRequest requset) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 1、接受请求参数 做转化
		String shopStr = HttpServletRequestUtil.getString(requset, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());

			return modelMap;
		}

		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				requset.getSession().getServletContext());

		if (commonsMultipartResolver.isMultipart(requset)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) requset;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "图片不能为空！");
			return modelMap;
		}
		// 2、注册店铺
		if (shop != null && shopImg != null) {
			PersonInfo owner = new PersonInfo();
			// SESSION todo
			owner.setUserId(1L);
			shop.setOwner(owner);
			// 注册
			ShopExecution se;
			try {
				se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());

				if (se.getState() == ShopStateEnum.CHECK.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (ShopOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
			// 3、最终返回结果
			return modelMap;

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息！");

			return modelMap;
		}

	}

//	private static void inputStreamToFile(InputStream ins, File file) {
//
//		OutputStream os = null;
//		try {
//			os = new FileOutputStream(file);
//
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while ((bytesRead = ins.read(buffer)) != -1) {
//				os.write(buffer, 0, bytesRead);
//			}
//		} catch (Exception e) {
//			throw new RuntimeException("调用inputStreamToFile出错:" + e.getMessage());
//		} finally {
//			try {
//				if (os != null) {
//					os.close();
//				}
//				if (ins != null) {
//					ins.close();
//				}
//			} catch (IOException e) {
//				throw new RuntimeException("调用inputStreamToFile关闭IO出错:" + e.getMessage());
//			}
//
//		}
//
//	}

}