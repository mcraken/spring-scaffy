package com.scaffy.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Configuration
public class GsonConverterConfig {
	
	@Bean
	public GsonHttpMessageConverter createGsonHttpMessageConverter() {
		
		Gson gson = new GsonBuilder()
		.setDateFormat("dd/MM/yyyy")
		.setExclusionStrategies(new AnnotationExeclusionStrategy())
		.create();

		GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
	
		gsonConverter.setGson(gson);

		return gsonConverter;
	}
}
