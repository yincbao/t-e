package org.baoting.te.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 
* @ClassName: Mongo.java 
* @Description: 
* @author YinChang-bao
* @date Nov 16, 2015
*
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Mongo {
	
	String database();
	String collection();

}