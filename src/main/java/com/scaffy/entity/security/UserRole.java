package com.scaffy.entity.security;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 *
 */
@Entity
@Table(name = "USER_ROLE")
@Cacheable(true)
public class UserRole implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "role_name")
	private String name;

	/**
	 * <p>Constructor for Role.</p>
	 */
	public UserRole() {
	}
	
	public UserRole(String name) {
		this.name = name;
	}


	/**
	 * <p>Getter for the field <code>name</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * <p>createAuthority.</p>
	 *
	 * @return a {@link org.springframework.security.core.GrantedAuthority} object.
	 */
	public GrantedAuthority createAuthority() {
		
		return new GrantedAuthority() {
			
			private static final long serialVersionUID = 1L;

			public String getAuthority() {
				return name;
			}
		};
	}

}
