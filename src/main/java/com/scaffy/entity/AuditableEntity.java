package com.scaffy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * <p>AuditableEntity class.</p>
 *
 * @author 	Sherief Shawky
 * @Email 	sheshawky@informatique-eg.com
 *
 * An auditable entity that get updated automatically by spring and JPA with 4 values
 * related to creation and update.
 * @version $Id: $Id
 */
@MappedSuperclass
public class AuditableEntity {
	
	@CreatedBy
	@Column(name = "createBy")
	private String createdBy;
	
	@CreatedDate
	@Column(name = "createDate")
	@Temporal(TemporalType.DATE)
	private Date createDate;
	
	@LastModifiedBy
	@Column(name = "updateBy")
	private String lastUpdatedBy;
	
	@LastModifiedDate
	@Column(name = "updateDate")
	@Temporal(TemporalType.DATE)
	private Date lastUpdateDate;

	/**
	 * <p>Getter for the field <code>createdBy</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * <p>Getter for the field <code>createDate</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * <p>Getter for the field <code>lastUpdatedBy</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	/**
	 * <p>Getter for the field <code>lastUpdateDate</code>.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	
}
