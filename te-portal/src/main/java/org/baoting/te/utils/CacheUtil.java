package org.baoting.te.utils;
/*package com.hoperun.ism.utils;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hoperun.ism.constaints.IsmConstaints;
import com.hoperun.ubi.cache.CacheManager;

*//**
 * 
* @ClassName: CacheUtil 
* @Description: 
* @author YinChang-bao
* @date Nov 13, 2015 9:59:57 AM 
*
 *//*
public class CacheUtil {

	private static final Log logger = LogFactory.getLog(CacheUtil.class);
	
	*//**
	 * 
	* @Description: try to fetch a key in cache
	* @param key 
	* @param clazz return type
	 *//*
	public static<T> T tryCache(String key,Class<T> clazz){
		T t = CacheManager.getMemCachedCache().get(key, clazz, null);
		if(t!=null&&logger.isDebugEnabled()){
			logger.debug("cache key:[ "+key+" ], hits ");
		}
		return t;
		return null;
			
	}
	
	*//**
	 * 
	* @Description: save  or update k-v to cache, a all key and value should be able to be Serialized, since mostly we use a distributed cache. and even can be persistence
	* @param k key
	* @param v value
	 *//*
	public static <K extends java.io.Serializable,V extends java.io.Serializable> void updateCache(K k,V v ){
		if(v!=null)
			CacheManager.getMemCachedCache().set(k, v);
	}
	
	*//**
	 * 
	* @Description: save  or update k-v to cache
	*
	 *//*
	public static void updateCache(String key,Map<? extends java.io.Serializable,? extends java.io.Serializable> value ){
		if(value!=null)
			CacheManager.getMemCachedCache().set(key,value);
	}
	
	public static void updateCache(String key,Collection<? extends java.io.Serializable> value ){
		if(value!=null)
			CacheManager.getMemCachedCache().set(key,value);
	}
	
	
	public static String buildRadiusKey(String item,Long vehicleId,Double latitude,Double longitude,Double radius){
		return item+vehicleId+""+latitude.doubleValue()+""+longitude.doubleValue()+""+radius.doubleValue();
	}
}
*/