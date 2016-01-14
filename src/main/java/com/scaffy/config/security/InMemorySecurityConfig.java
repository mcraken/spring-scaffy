package com.scaffy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class InMemorySecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${security.inmemory.users}")
	private String users;
	
	@Value("${security.inmemory.root}")
	private String rootUrl;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		String[] userParts;
		 
		for(String user : users.split(",")){
			
			userParts = user.split(":");
			
			auth.inMemoryAuthentication()
			.withUser(userParts[0])
			.password(userParts[1])
			.authorities(userParts[2].split("\\+"));
		}
		
	}
	
	/** {@inheritDoc} */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {

		AuthenticationManager authenticationManager = super.authenticationManager(); 

		return authenticationManager;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher(rootUrl).authorizeRequests().antMatchers(rootUrl);
	}
	
}
