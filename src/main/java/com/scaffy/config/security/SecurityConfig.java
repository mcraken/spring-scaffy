package com.scaffy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.inMemoryAuthentication()
		.withUser("mc").password("pass").authorities("ROLE_USER");
		
	}
	
	@Configuration
	@Order(100)
	public static class formSecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			
			http
			.antMatcher("/**")
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/oauth/**").authenticated()
				.antMatchers("/login", "/**").permitAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.and()
			.logout()
				.logoutSuccessUrl("/login");
		}
	}
	
	@Configuration
	@Order(101)
	public static class apiSecurityConfigurations extends WebSecurityConfigurerAdapter {
		
		@Override
		@Bean
		protected AuthenticationManager authenticationManager() throws Exception {
			
			AuthenticationManager authenticationManager = super.authenticationManager(); 
			
			return authenticationManager;
		}
		
	}
	
}
