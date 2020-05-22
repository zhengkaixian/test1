package com.zero.base.util;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;

/**
 * @Description: 两个对象间，相同属性名之间进行转换
 * 
 * @ClassName: EntityObjectConverter
 * @Copyright: Copyright (c) 2013
 *
 * @author
 * @date 2013-11-4 上午09:55:14
 * @version V2.0
 */
public class EntityObjectConverter {
	/*
	 * 实例化对象
	 */
	private static Mapper map = DozerBeanMapperBuilder.buildDefault();

	/**
	 * 将目标对象转换为指定对象，相同属性名进行属性值复制
	 * @param <T>
	 * @param source
	 * @param cls
	 * @return
	 */
	public static <T> T getObject(Object source, Class<T> cls) {
		if (source == null) {
			return null;
		}
		return (T) map.map(source, cls);
	}

	/**
	 * 两个对象之间相同属性名的属性值复制
	 * @param source
	 * @param target
	 */
	public static void setObject(Object source, Object target) {
		map.map(source, target);
	}

	/**
	 * 对象集合中对象相同属性名的属性值复制
	 * @param source
	 * @param cls
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getList(List source, Class cls) {
		List listTarget = new ArrayList();
		if (source != null) {
			for (Object object : source) {
				Object objTarget = EntityObjectConverter.getObject(object, cls);
				listTarget.add(objTarget);
			}
		}
		return listTarget;
	}
}
