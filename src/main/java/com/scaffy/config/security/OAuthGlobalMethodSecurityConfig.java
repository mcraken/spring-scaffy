package com.scaffy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

/**
 * <p>GlobalMethodSecurityConfig class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 *
 * A global method security configurations class. This class configures the means for
 * securing methods using PreAuthorize annotations instead of using explicit links.
 * @version $Id: $Id
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class OAuthGlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration{
	
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * <p>oauthExpressionHandler.</p>
     *
     * @return a {@link org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler} object.
     */
    @Bean
    public OAuth2MethodSecurityExpressionHandler oauthExpressionHandler() {
        return new OAuth2MethodSecurityExpressionHandler();
    }

    /** {@inheritDoc} */
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return authenticationManager;
    }
}
