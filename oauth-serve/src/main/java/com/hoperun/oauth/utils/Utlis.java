package com.hoperun.oauth.utils;

import java.lang.management.ManagementFactory;

/**
 * @ClassName Utlis.java
 * @Description 
 * @author yin_changbao
 * @Date Jun 24, 2016 1:52:14 PM
 *
 */
public final class Utlis {

	
	public static String getPid() {
		String name = ManagementFactory.getRuntimeMXBean().getName();  
		return name.split("@")[0];
	}
}
