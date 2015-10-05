package com.scaffy.entity.security;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Entity
@Table(name = "PAS_User")
@Cacheable(true)
public class ScaffyUser {
	@Id
	private String username;
	
	@Column
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "pas_user_role", joinColumns = {
				@JoinColumn(name = "username")
			}, inverseJoinColumns = {
				@JoinColumn(name = "role_name")	
			})
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private List<Role> roles;
	
	/**
	 * <p>Constructor for PASUser.</p>
	 */
	public ScaffyUser() {
		
	}

	/**
	 * <p>Constructor for PASUser.</p>
	 *
	 * @param username a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 */
	public ScaffyUser(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * <p>Getter for the field <code>username</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <p>Getter for the field <code>password</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * <p>Getter for the field <code>roles</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<Role> getRoles() {
		return roles;
	}
	
	/**
	 * <p>createUser.</p>
	 *
	 * @return a {@link org.springframework.security.core.userdetails.User} object.
	 */
	public User createUser() {
		
		ArrayList<GrantedAuthority> grantedAuthorities = getAuthorities();
		
		return new User(username, password, grantedAuthorities);
	}

	public ArrayList<GrantedAuthority> getAuthorities() {
		
		ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		
		for(Role role : roles)
			grantedAuthorities.add(role.createAuthority());
		
		return grantedAuthorities;
	}
}
