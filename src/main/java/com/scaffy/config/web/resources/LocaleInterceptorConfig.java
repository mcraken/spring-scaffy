package com.scaffy.config.web.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class LocaleInterceptorConfig {
	
	@Value("${resourcebundle.param}")
	private String localeParam;
	
	@Bean
	private HandlerInterceptor localeInterceptor() {
		
		LocaleChangeInterceptor localInterceptor = new LocaleChangeInterceptor();
		
		localInterceptor.setParamName(localeParam);
		
		return localInterceptor;
	}
}
