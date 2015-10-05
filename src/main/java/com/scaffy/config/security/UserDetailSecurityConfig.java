package com.scaffy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.scaffy.service.UserDetailSecurityService;

@Configuration
@EnableWebSecurity
public class UserDetailSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailSecurityService userDetailService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth
		.userDetailsService(userDetailService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	/** {@inheritDoc} */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {

		AuthenticationManager authenticationManager = super.authenticationManager(); 

		return authenticationManager;
	}
	
}
