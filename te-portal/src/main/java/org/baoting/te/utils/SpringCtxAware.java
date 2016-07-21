package org.baoting.te.utils;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class SpringCtxAware implements ApplicationContextAware{
	
	private final Log log = LogFactory.getLog(SpringCtxAware.class);
	
	private static ConfigurableApplicationContext  applicationContext = null;
	
	public SpringCtxAware(){
		log.info("initalling...");
	}
	public static ConfigurableApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringCtxAware.applicationContext = (ConfigurableApplicationContext)applicationContext; 
		
	}
	
	
	public void loadBean(String configLocationString){
		 XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) getApplicationContext().getBeanFactory());
		 beanDefinitionReader.setResourceLoader(getApplicationContext());
		 beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(getApplicationContext())); 
		 String[] configLocations = new String[]{configLocationString};  
		 try {
			 if(configLocations!=null&&configLocations.length>0){
				 for(String l:configLocations)
						beanDefinitionReader.loadBeanDefinitions(getApplicationContext().getResources(l));
			 }
		 } catch (BeanDefinitionStoreException e) {
			log.error(e.getMessage(), e);
		 } catch (IOException e) {
			 log.error(e.getMessage(), e);
		}
	}
	
	public void loadPorcess(String xmlFile){
		ClassPathResource resource = new ClassPathResource(xmlFile);
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader((BeanDefinitionRegistry) getApplicationContext().getBeanFactory());
		reader.loadBeanDefinitions(resource);
	}
	
}