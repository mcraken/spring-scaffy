package com.scaffy.config.web.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ResourceBundleConfig {

	@Value("${resourcebundle.base}")
	private String baseName;
	
	/**
	 * <p>messageSource.</p>
	 *
	 * @return a {@link org.springframework.context.support.ResourceBundleMessageSource} object.
	 */
	@Bean
	public ResourceBundleMessageSource messageSource(){
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		
		messageSource.setBasename(baseName);
		
		return messageSource;
	}
}
