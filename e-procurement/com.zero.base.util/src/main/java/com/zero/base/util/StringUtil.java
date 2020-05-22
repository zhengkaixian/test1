package com.zero.base.util;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;

/** 
 * @ClassName: StringUtil 
 * @Description: TODO (这里用一句话描述这个类的作用)
 * @author: think
 * @date: 2018年11月29日 下午1:58:09
 *
 */

public class StringUtil {
	/**
	 * @Title: 业务系统统一用改方法获取主键
	 * @Description: 获取UUID 带-标识
	 * @return String 返回类型
	 * @throws
	 */
	public static String getUuid() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str;
	}
	/**
	 * @Title: isEmpty
	 * @Description: 判断字符串是否为空
	 * @param @param string
	 * @return boolean
	 * @throws
	 */
	public static boolean isEmpty(String string) {
		boolean result = false;
		if (string == null || "".equals(string.trim())) {
			result = true;
		}
		return result;
	}
	/**
	 * 验证Object是否为空,object instanceof String
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object) {
		boolean result = false;
		if (object == null || "".equals(object.toString().trim())) {
			result = true;
		}
		return result;
	}
	
	/**
	* @Title: doNullStr
	* @Description: 将字符串为"null"或空对象转化为字符串""
	* @param @param obj
	* @param @return    参数说明
	* @return String    返回类型
	* @throws
	*/ 
	public static String doNullStr(Object obj){
		String str = "";
		if(obj != null){
			str = String.valueOf(obj);
			if(str.equals("null")){
				str = "";
			}
		}
		return str.trim();
	}
	/**
	* @Title: trim
	* @Description: 空字符串处理
	* @param @param str
	* @param @return    参数说明
	* @return String    返回类型
	* @throws
	*/ 
	public static String trim(String str) {
		return str == null ? null : str.trim();
	}
	
	public static String replace(String str,String oldStr , String newStr) {
		if(str != null && str.indexOf(oldStr) > -1){
			str = str.replace(oldStr, newStr);
		}
		return str;
	}
	
	/**
	  * 转义"_"
	 * @param params
	 * @param filterKeys
	 */
	public static String filterFormater(String str) {
		if(!StringUtil.isEmpty(str)){
			str = str.replaceAll("_", "/_");
		}
		return str;
	}
	
	/**
	  * 转义"_"
	 * @param params
	 * @param filterKeys
	 */
	public static void filterFormater(Map<String, Object> params, String[] filterKeys) {
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (ArrayUtils.contains(filterKeys, entry.getKey())) {
				String value = (String) entry.getValue();
				if(!StringUtil.isEmpty(value)){
					value = value.replaceAll("_", "/_");
				}
				params.put(entry.getKey(), value);
			}
		}
	}
	
	/**
	 * 转义"_"
	 * @param object
	 * @param filterKeys
	 */
	public static Object filterFormater(Object obj, String[] filterKeys) {
		Map<String, Object> params = objectToMap(obj);
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (ArrayUtils.contains(filterKeys, entry.getKey())) {
				String value = (String) entry.getValue();
				if(!StringUtil.isEmpty(value)){
					value = value.replaceAll("_", "/_");
				}
				params.put(entry.getKey(), value);
			}
		}
		obj = mapToObject(params, obj.getClass());
		return obj;
	}
	
	/**
	 * Object转Map
	 * @param obj
	 * @return
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		try {
			//法一：使用reflect进行转换 
		    if(obj == null){    
	            return null;    
	        }   
	        Map<String, Object> map = new HashMap<String, Object>();    
	        Field[] declaredFields = obj.getClass().getDeclaredFields();    
	        for (Field field : declaredFields) {    
	            field.setAccessible(true);  
	            map.put(field.getName(), field.get(obj));  
	        }    
	        return map;  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}    
	
	/**
	 * Map转Object
	 * @param map
	 * @param beanClass
	 * @return
	 * @throws Exception
	 */
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
		//法一：使用reflect进行转换 
		if (map == null) {
			return null;    
		}
		try {
			Object obj = beanClass.newInstance();
			Field[] fields = obj.getClass().getDeclaredFields();   
	        for (Field field : fields) {    
	            int mod = field.getModifiers();    
	            if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
	                continue;    
	            }    
	            field.setAccessible(true);    
	            field.set(obj, map.get(field.getName()));   
	        }   
	        return obj;   
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}  
		
		return null;
        
        //法二：使用Introspector进行转换 
		/*
		if(map == null) {
			return null;    
		}
        Object obj = beanClass.newInstance();  
        BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());    
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();    
        for (PropertyDescriptor property : propertyDescriptors) {  
            Method setter = property.getWriteMethod();    
            if (setter != null) {  
                setter.invoke(obj, map.get(property.getName()));   
            }  
        }  
        return obj;
        */
    }  
	
	/**
	  * 查找字符串中某个子串第times次出现的下标
	 * @param str 源字符串
	 * @param subStr 子字符串
	 * @param times 第times次
	 * @return
	 */
	public static int getSubStrIndex(String str, String c, int times) {
        int index = 0;
        String[] arr = str.split(c);
        int length = arr.length > times ? times : arr.length;
        for (int i = 0; i < length; i++) {
            index += arr[i].length();
        }
        return index + times - 1;
    }
}
