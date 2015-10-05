package com.scaffy.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private TokenStore tokenStore;
	
	@Value("${security.oauth.resourceId}")
	private String resourceId;
	
	@Value("${security.oauth.rootUrl}")
	private String rootUrl;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources)
			throws Exception {
		
		resources.resourceId(resourceId).tokenStore(tokenStore);
		
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		.antMatcher(rootUrl)
		.authorizeRequests()
		.antMatchers(rootUrl).authenticated()
			.and()
		.csrf()
			.disable();
	}
	
}
