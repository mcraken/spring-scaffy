package com.scaffy.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class UserlessSecurityConfig extends WebSecurityConfigurerAdapter {
	
	/** {@inheritDoc} */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {

		AuthenticationManager authenticationManager = super.authenticationManager(); 

		return authenticationManager;
	}
	
}
