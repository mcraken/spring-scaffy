package com.scaffy.config.web;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

/**
 * <p>CustomValidator class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 *
 * A custom auto validation magic. In most deployment environment, this class is created
 * automatically. Had to create one manually due to limitations in the WebSphere.
 * @version $Id: $Id
 */
public class CustomValidator implements Validator{

	private SpringValidatorAdapter springValidator;
	
	/**
	 * <p>Constructor for CustomValidator.</p>
	 *
	 * @param validator a {@link javax.validation.Validator} object.
	 */
	public CustomValidator(javax.validation.Validator validator) {
		this.springValidator = new SpringValidatorAdapter(validator);
	}

	/** {@inheritDoc} */
	public boolean supports(Class<?> clazz) {
		
		return springValidator.supports(clazz);
	}

	/** {@inheritDoc} */
	public void validate(Object target, Errors errors) {
		
		springValidator.validate(target, errors);
	}

}
