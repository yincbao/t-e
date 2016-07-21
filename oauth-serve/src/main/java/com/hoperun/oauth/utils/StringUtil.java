package com.hoperun.oauth.utils;

/**
 * @ClassName StringUtil.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 16, 2016 11:04:57 AM
 *
 */
public class StringUtil {
	
	private StringUtil(){}
	
	public static boolean isEmpty(String str){
		return str==null||"".equalsIgnoreCase(str.trim());
	}

}
