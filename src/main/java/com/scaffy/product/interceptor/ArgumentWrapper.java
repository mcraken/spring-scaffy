package com.scaffy.product.interceptor;

public class ArgumentWrapper {
	
	private Object[] arguments;
	
	public ArgumentWrapper(Object[] args) {
		this.arguments = args;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getArgument(int index, Class<T> type) {
		return (T) arguments[index];
	}
}
