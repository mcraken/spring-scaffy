/**
 * 
 */
package com.scaffy.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
@Entity
@Table(name = "OAUTH_CLIENT_DETAILS")
public class OAuthClient {
	
	@Id
	@Column(name = "CLIENT_ID")
	private String clientId;
	
	@Column(name = "RESOURCE_IDS")
	private String resourceId;
	
	@Column(name = "CLIENT_SECRET")
	private String secret;
	
	@Column(name = "SCOPE")
	private String scope;
	
	@Column(name = "AUTHORIZED_GRANT_TYPES")
	private String grantTypes;
	
	@Column(name = "WEB_SERVER_REDIRECT_URI")
	private String redirectUri;
	
	@Column(name = "AUTHORITIES")
	private String authorities;
	
	@Column(name = "ACCESS_TOKEN_VALIDITY")
	private Long accessTokenValidity;
	
	@Column(name = "REFRESH_TOKEN_VALIDITY")
	private Long refreshTokenValdidty;
	
	@Column(name = "ADDITIONAL_INFORMATION")
	private String additionalInfo;
	
	@Column(name = "AUTOAPPROVE")
	private String autoApprove;

	public OAuthClient() {
		
	}
	
	public OAuthClient(String clientId, String resourceId, String secret,
			String scope, String grantTypes, String redirectUri,
			String authorities, Long accessTokenValidity,
			Long refreshTokenValdidty, String additionalInfo, String autoApprove) {

		this.clientId = clientId;
		this.resourceId = resourceId;
		this.secret = secret;
		this.scope = scope;
		this.grantTypes = grantTypes;
		this.redirectUri = redirectUri;
		this.authorities = authorities;
		this.accessTokenValidity = accessTokenValidity;
		this.refreshTokenValdidty = refreshTokenValdidty;
		this.additionalInfo = additionalInfo;
		this.autoApprove = autoApprove;
	}

	public String getClientId() {
		return clientId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public String getSecret() {
		return secret;
	}

	public String getScope() {
		return scope;
	}

	public String getGrantTypes() {
		return grantTypes;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public String getAuthorities() {
		return authorities;
	}

	public Long getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public Long getRefreshTokenValdidty() {
		return refreshTokenValdidty;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public String getAutoApprove() {
		return autoApprove;
	}
	
}
