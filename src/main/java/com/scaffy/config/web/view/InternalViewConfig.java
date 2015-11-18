package com.scaffy.config.web.view;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class InternalViewConfig {
	
	@Value("${view.internal.prefix}")
	private String prefix;
	
	@Value("${view.internal.suffix}")
	private String suffix;
	
	@Bean
	public InternalResourceViewResolver viewResolver(){
		
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix(prefix);
		
		viewResolver.setSuffix(suffix);
		
		return viewResolver;
	}
}
