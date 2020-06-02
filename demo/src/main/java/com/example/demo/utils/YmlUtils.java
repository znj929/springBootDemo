package com.example.demo.utils;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

public class YmlUtils {

	// 默认配置文件
	private static String PROPERTY_NAME = "application.yml";
	
	// 读取配置
	public static Object getYmlConfig(Object key) {
		Resource resource = new ClassPathResource(PROPERTY_NAME);
		Properties properties = null;
		try {
			YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
			yamlFactory.setResources(resource);
			properties = yamlFactory.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return properties.get(key);
	}
}
