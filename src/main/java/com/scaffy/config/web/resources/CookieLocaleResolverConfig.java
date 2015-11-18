package com.scaffy.config.web.resources;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
public class CookieLocaleResolverConfig {

	@Value("${resourcebundle.default}")
	private String defaultLocale;
	
	/**
	 * <p>localeResolver.</p>
	 *
	 * @return a {@link org.springframework.web.servlet.i18n.CookieLocaleResolver} object.
	 */
	@Bean
	public CookieLocaleResolver localeResolver(){
		
		CookieLocaleResolver localeResolver = new CookieLocaleResolver();
		
		localeResolver.setDefaultLocale(new Locale(defaultLocale));
		
		return localeResolver;
	}
}
