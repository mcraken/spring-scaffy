package com.scaffy.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.stereotype.Service;

import com.scaffy.entity.security.ScaffyUser;
import com.scaffy.repository.UserRepository;

@Service
public class SimpleLDAPAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private UserRepository userRepository;

	private LdapTemplate ldapTemplate;
	
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
	
	@PostConstruct
	public void init() throws Exception {

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
		
		boolean authed = ldapTemplate.authenticate(
				ldapBase, 
				ldapUid + "=" + username, 
				password
				);

		if(authed){

			ScaffyUser user = userRepository.findByUsernameLike(username);
			
			if(user == null)
				throw new AuthenticationCredentialsNotFoundException("Bad Credentials");
			
			List<GrantedAuthority> authorities = user.getAuthorities();
			
			return new UsernamePasswordAuthenticationToken(new User(username, password, authorities), password, authorities);
			
		} else {
			
			throw new BadClientCredentialsException();
		}
	}

	public boolean supports(Class<?> authentication) {
		
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
