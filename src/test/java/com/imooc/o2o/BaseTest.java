package com.imooc.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 配置SPRING和junit整合,junit启动时加载springIOC容器
 * 
 * @author Dell
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
//配置文件路径
@ContextConfiguration({ "classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml" })
public class BaseTest {

}
