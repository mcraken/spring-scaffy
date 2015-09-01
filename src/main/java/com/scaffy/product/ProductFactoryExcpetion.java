package com.scaffy.product;

public class ProductFactoryExcpetion extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ProductFactoryExcpetion(Throwable e) {
		super("Product creation failed", e);
	}
}
