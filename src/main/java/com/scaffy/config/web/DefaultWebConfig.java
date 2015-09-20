package com.scaffy.config.web;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.scaffy.controller")
public class DefaultWebConfig extends WebMvcConfigurerAdapter{

	@Value("${converter.gson.dateFormat}")
	private String gsonDateFormat;
	
	@Autowired
	private GsonHttpMessageConverter gsonHttpMessageConverter;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {

		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		converters.add(gsonHttpMessageConverter);
		
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		Collection<HandlerInterceptor> interceptors = applicationContext.getBeansOfType(HandlerInterceptor.class).values();
		
		for(HandlerInterceptor interceptor : interceptors)
			registry.addInterceptor(interceptor);
		
	}
	
	@Bean
	public GsonHttpMessageConverter createGsonHttpMessageConverter() {
		
		Gson gson = new GsonBuilder()
		.setDateFormat(gsonDateFormat)
		.setExclusionStrategies(new AnnotationExeclusionStrategy())
		.create();

		GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
	
		gsonConverter.setGson(gson);

		return gsonConverter;
	}
}
