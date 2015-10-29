/**
 * 
 */
package com.scaffy.entity.attachment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.scaffy.entity.AuditableEntity;
import com.scaffy.product.restful.RestModel;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */

@Entity
@Table(name = "ATTACHMENT")
@RestModel(
		secure = false,
		cacheable = false, 
		transactional = false
		)
public class Attachment extends AuditableEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACHMENT_ID_SEQ")
	@SequenceGenerator(name = "ATTACHMENT_ID_SEQ", sequenceName = "ATTACHMENT_ID_SEQ")
	private Long id;
	
	@Column(name = "CONTENT_TYPE")
	private String contentType;
	
	@Column(name = "CONTENT_NAME")
	private String contentName;

	private byte[] dataFile;
	
	@Column(name = "RESOURCE_ID")
	private String resourceId;
	
	@Column(name = "RESOURCE_NAME")
	private String resourceName;
	
	@Column(name = "TARGET_ID")
	private String targetId;
	
	@Column(name = "TARGET_NAME")
	private String targetName;
	
	@Column(name = "TEMP_ID")
	private Long tempId;

	public Attachment() {
		
	}
	
	public Attachment(String contentName, String contentType, byte[] dataFile, 
			String targetName, String resourceId) {
		
		this.contentName = contentName;
		this.contentType = contentType;
		this.dataFile = dataFile;
		this.targetName = targetName;
		this.resourceId = resourceId;
		
	}

	public Long getId() {
		return id;
	}

	public String getContentType() {
		return contentType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getTargetId() {
		return targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public Long getTempId() {
		return tempId;
	}
	
	public byte[] getDataFile() {
		return dataFile;
	}
}
