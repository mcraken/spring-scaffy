package com.scaffy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;

import com.google.gson.reflect.TypeToken;

public class ModelUnmarshaller {

	@Autowired 
	private GsonHttpMessageConverter httpMessageConverter;

	@Autowired 
	private Validator validator;
	
	public ModelUnmarshaller() {
	}

	public <T>T bind(String body, Class<T> modelType) {
		
		return httpMessageConverter.getGson().fromJson(body, modelType);
	}
	
	public <T>List<T> binds(String body, Class<T> modelType) {
		
		return httpMessageConverter.getGson().fromJson(body, new TypeToken<List<T>>(){}.getType());
	}
	
	public void validate(String modelName, Object model) throws BindException {
		
		BindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), modelName); 
		
		validator.validate(model, bindingResult);
		
		if(bindingResult.hasErrors())
			throw new BindException(bindingResult);
	}

}