package com.scaffy.dao;

import java.util.List;

import com.scaffy.acquisition.exception.InvalidCriteriaException;
import com.scaffy.acquisition.key.RestSearchKey;

public interface SearchDao {
	
	public <T> List<T> read(RestSearchKey key, Class<T> typeClass) throws InvalidCriteriaException;
}
