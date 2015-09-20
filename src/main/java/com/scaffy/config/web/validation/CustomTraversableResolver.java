package com.scaffy.config.web.validation;

import java.lang.annotation.ElementType;

import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.TraversableResolver;

/**
 * <p>CustomTraversableResolver class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 *
 * It's a dummy implementation for a resolver that looks for validation providers.
 * Had to use to fix a limitation in WebSphere that provides a deprecated built-in
 * validation provider.
 * @version $Id: $Id
 */
public class CustomTraversableResolver implements TraversableResolver {

	/** {@inheritDoc} */
	public boolean isCascadable(Object arg0, Node arg1, Class<?> arg2,
			Path arg3, ElementType arg4) {
		
		return true;
	}

	/** {@inheritDoc} */
	public boolean isReachable(Object arg0, Node arg1, Class<?> arg2,
			Path arg3, ElementType arg4) {
		
		return true;
	}

}
