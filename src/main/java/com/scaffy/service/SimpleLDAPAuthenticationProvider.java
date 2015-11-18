package com.scaffy.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;

import com.scaffy.entity.security.UserAccount;

public class SimpleLDAPAuthenticationProvider implements AuthenticationProvider{

	private EntityManager entityManager;

	private LdapTemplate ldapTemplate;

	private String ldapBase;

	private String ldapUid;

	public SimpleLDAPAuthenticationProvider(EntityManager entityManager,
			String ldapUrl, String ldapUserDn, String ldapPassword,
			String ldapBase, String ldapUid) throws Exception {

		this.entityManager = entityManager;
		this.ldapBase = ldapBase;
		this.ldapUid = ldapUid;

		LdapContextSource contextSource = new LdapContextSource();

		contextSource.setUrl(ldapUrl);
		contextSource.setUserDn(ldapUserDn);
		contextSource.setPassword(ldapPassword);
		contextSource.afterPropertiesSet();

		ldapTemplate = new LdapTemplate(contextSource);

		ldapTemplate.afterPropertiesSet();
	}

	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {

		String username = getUsername(authentication);

		String password = getPassword(authentication);

		return doAuthenticate(username, password);

	}

	private String getUsername(Authentication authentication) {

		String username = authentication.getName();

		if(username == null || username.isEmpty())
			throw new BadClientCredentialsException();

		return username;
	}

	private String getPassword(Authentication authentication) {

		Object password = authentication.getCredentials();

		if(password == null || password.toString().isEmpty()){
			throw new BadClientCredentialsException();
		}

		return password.toString();
	}

	private Authentication doAuthenticate(String username, String password) {
		
		EntityTransaction t = entityManager.getTransaction();
		
		try{
			
			t.begin();
			
			boolean authed = ldapTemplate.authenticate(
					ldapBase, 
					ldapUid + "=" + username, 
					password
					);

			if(authed){

				UserAccount user = entityManager.find(UserAccount.class, username);

				if(user == null)
					throw new AuthenticationCredentialsNotFoundException("Bad Credentials");

				List<GrantedAuthority> authorities = user.getAuthorities();

				return new UsernamePasswordAuthenticationToken(new User(username, password, authorities), password, authorities);

			} else {

				throw new BadClientCredentialsException();
			}
		} finally {
			
			t.commit();
		}
	}

	public boolean supports(Class<?> authentication) {

		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
