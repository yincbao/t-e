package org.baoting.te.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.baoting.te.constaints.ExceptionCodeEnum;
import org.baoting.te.exceptions.ConfigException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Log4jConfigurer;


/**
 * 
 * 
* @ClassName: ConfigException.java 
* @Description: 
* @author YinChang-bao
* @date Nov 16, 2015
*
 */
public class SystemConfig {

	private static final Log logger = LogFactory.getLog(SystemConfig.class);

	private static Properties configProperties = null;
	private static final String PROPS_FILE_NAME = "system.properties";


	static {
		loadConfig();
	}

	public static void loadConfig() {
		try {
			logger.debug("loadConfig() starts.");
			InputStream is = SystemConfig.class.getClassLoader().getResourceAsStream(
					SystemConfig.PROPS_FILE_NAME);

			if (is == null) {
				throw new IOException("Properties file: " + SystemConfig.PROPS_FILE_NAME + " is not found!");
			}
			configProperties = new Properties();
			configProperties.load(is);
			is.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			logger.debug("loadConfig() ends.");
		}
	}

	public static String getProperty(String key) {

		String value = "";

		if (configProperties != null) {
			value = configProperties.getProperty(key);
		} else {
			throw new ConfigException("TC(500)", "Properties have not been loaded!");
		}

		return value;

	}
	public static String getProperty(String property, String def) {
		String retVal = null;
				if (configProperties != null){
			retVal = configProperties.getProperty(property, def).trim();
		}else{
			retVal = def;
		}
		return retVal;
	}

	
	public static void loadLog4j(String log4j){
		try {
			Log4jConfigurer.initLogging(log4j);
		} catch (FileNotFoundException e) {
			try {
				String path = new ClassPathResource(log4j).getURL().toString();
				logger.info("log4j system will use " + path);
				Log4jConfigurer.initLogging(path);
			} catch (Exception e1) {
				System.err.println("Not found log4j config file etc/log4j.properties" +
						" or classpath:log4j.properties");
			}
		}
	}
	public static String getResourcePath(String fileName){
		return SystemConfig.class.getClassLoader().getResource(fileName).toExternalForm();
	}
	
	 public static int getIntProperty(String property) {
			int retVal = 0;
			if (configProperties != null){
				try{
					retVal = Integer.parseInt(configProperties.getProperty(property).trim());
				}catch(NumberFormatException e){
					throw new ConfigException(ExceptionCodeEnum.numberFormatException,e);
				}
				
			}
			return retVal;
		}
	  
	  public static int getIntProperty(String property,int def) {
			int retVal = 0;
			if (configProperties != null){
				try{
					retVal = Integer.parseInt(configProperties.getProperty(property).trim());
				}catch(NumberFormatException e){
					retVal = def;
					logger.warn("no property :"+property+" found, return default value: "+def);
				}
				
			}
			return retVal;
		}
	  
	  public static long getLongProperty(String property) {
			long retVal = 0;
			if (configProperties != null){
				try{
					retVal = Long.parseLong(configProperties.getProperty(property).trim());
				}catch(NumberFormatException e){
					throw new ConfigException(ExceptionCodeEnum.numberFormatException,e);
				}
				
			}
			return retVal;
		}

	  public static long getLongProperty(String property,long def) {
			long retVal = 0;
			if (configProperties != null){
				try{
					retVal = Integer.parseInt(configProperties.getProperty(property).trim());
				}catch(NumberFormatException e){
					retVal = def;
					logger.warn("no property :"+property+" found, return default value: "+def);
				}
				
			}
			return retVal;
		}
	  
	  public static double getDoubleProperty(String property) {
		  double retVal = 0;
			if (configProperties != null){
				try{
					retVal = Double.parseDouble(configProperties.getProperty(property).trim());
				}catch(NumberFormatException e){
					throw new ConfigException(ExceptionCodeEnum.numberFormatException,e);
				}
				
			}
			return retVal;
		}
	  
	  public static boolean getBooleanProperty(String property,boolean def) {
		  boolean retVal = def;
			if (configProperties != null){
				try{
					retVal = Boolean.parseBoolean(configProperties.getProperty(property).trim());
				}catch(NumberFormatException e){
					throw new ConfigException(ExceptionCodeEnum.numberFormatException,e);
				}
				
			}
			return retVal;
		}
	  
	  public static double getDoubleProperty(String property,double def) {
		  double retVal = 0;
			if (configProperties != null){
				try{
					retVal = Double.parseDouble(configProperties.getProperty(property).trim());
				}catch(NumberFormatException e){
					retVal = def;
					logger.warn("no property :"+property+" found, return default value: "+def);
				}
			}
			return retVal;
		}


}
