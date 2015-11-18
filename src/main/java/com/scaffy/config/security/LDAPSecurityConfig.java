package com.scaffy.config.security;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.scaffy.service.SimpleLDAPAuthenticationProvider;

@Configuration
@EnableWebSecurity
public class LDAPSecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private EntityManager entityManager;
	
	@Value("${security.ldap.url}")
	private String ldapUrl;
	
	@Value("${security.ldap.userDn}")
	private String ldapUserDn;
	
	@Value("${security.ldap.password}")
	private String ldapPassword;
	
	@Value("${security.ldap.base}")
	private String ldapBase;
	
	@Value("${security.ldap.uid}")
	private String ldapUid;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.authenticationProvider(
				new SimpleLDAPAuthenticationProvider(
						entityManager, 
						ldapUrl, 
						ldapUserDn, 
						ldapPassword, 
						ldapBase, 
						ldapUid
						)
				);
	}
	
	/** {@inheritDoc} */
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {

		AuthenticationManager authenticationManager = super.authenticationManager(); 

		return authenticationManager;
	}
	
}
