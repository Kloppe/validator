package com.gl365.validator.common;

import com.google.gson.Gson;

/**
 * < Google公司jar包帮助对象转JsonString >
 * 
 * 输出日志方便阅览定位
 * 
 * @author hui.li 2017年4月21日 - 下午3:32:56
 * @Since 1.0
 */
public class JsonUtils {

	private static Gson gson = new Gson();
	
	public static String toJsonString(Object object) {
		return gson.toJson(object);
	}
	
	public static <T> T toBean(String jsonStr, Class<T> beanClass){
		return gson.fromJson(jsonStr, beanClass);
    }
}
