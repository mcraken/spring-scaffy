package com.scaffy.config.web;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;

@Configuration
public class ValidatorConfig {
	
	@Bean
	public Validator validator() {
		
		ValidatorFactory factory = Validation.byDefaultProvider().configure().traversableResolver(new CustomTraversableResolver()).buildValidatorFactory();
		
		return new CustomValidator(factory.getValidator());
	}
	
}
