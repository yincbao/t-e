package org.baoting.te.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * ClassName: MongoAnnotationProcessor
 * 
 * @description
 * @author yin_changbao
 * @Date Oct 16, 2015
 *
 */
public class MongoAnnotationProcessor {

	public static <T> String getDatabase(Class<T> clazz) {
		com.hoperun.ism.annotation.Mongo schema = annotation(clazz);
		if (schema != null && !StringUtil.isEmpty(schema.database()))
			return schema.database();
		return null;
	}

	public static <T> String getCollection(Class<T> clazz) {
		com.hoperun.ism.annotation.Mongo schema = annotation(clazz);
		if (schema != null && !StringUtil.isEmpty(schema.database()))
			return schema.collection();
		return null;
	}

	private static <T> com.hoperun.ism.annotation.Mongo annotation(Class<T> clazz) {
		if (clazz == null)
			return null;
		if (!clazz.isAnnotationPresent(com.hoperun.ism.annotation.Mongo.class))
			return null;
		Map<Class<?>, Object> adapterMap = new HashMap<Class<?>, Object>();
		com.hoperun.ism.annotation.Mongo schema = clazz.getAnnotation(com.hoperun.ism.annotation.Mongo.class);
		return schema;
	}
}
