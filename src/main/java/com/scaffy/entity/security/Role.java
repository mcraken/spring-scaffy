package com.scaffy.entity.security;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * <p>Role class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	sheshawky@informatique-eg.com
 *
 * A lookup table for all defined system roles.
 * @version $Id: $Id
 */
@Entity
@Table(name = "PAS_ROLE")
@Cacheable(true)
public class Role {

	@Id
	@Column(name = "role_name")
	private String name;

	/**
	 * <p>Constructor for Role.</p>
	 */
	public Role() {
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
