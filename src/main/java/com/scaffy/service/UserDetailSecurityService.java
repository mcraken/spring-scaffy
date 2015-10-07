package com.scaffy.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.scaffy.entity.security.UserAccount;

public class UserDetailSecurityService implements UserDetailsService{

	private EntityManager entityManager;

	public UserDetailSecurityService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		EntityTransaction t = entityManager.getTransaction();
		
		try{
			
			t.begin();
			
			UserAccount user = entityManager.find(UserAccount.class, username);

			if(user == null)
				throw new UsernameNotFoundException(username);

			return user.createUser();
			
		} finally {
			
			t.commit();
		}
	}
}
