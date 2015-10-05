package com.scaffy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.scaffy.entity.security.ScaffyUser;
import com.scaffy.repository.UserRepository;

public class UserDetailSecurityService {
	@Autowired
	private UserRepository userRepository;
	
	/** {@inheritDoc} */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ScaffyUser user = userRepository.findByUsernameLike(username);
		
		if(user == null)
			throw new UsernameNotFoundException(username);

		return user.createUser();
	}
}
