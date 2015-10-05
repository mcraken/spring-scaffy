package com.scaffy.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class InMemoryTokenStoreConfig {

	@Bean
	public TokenStore tokenStore() {
		return new InMemoryTokenStore();
	}
}
