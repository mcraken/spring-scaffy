/**
 * 
 */
package com.scaffy.entity.attachment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
@Entity
@Table(name = "ATTACHMENT_TEMP")
public class AttachmentTemp {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ATTACHMENT_TEMP_ID_SEQ")
	@SequenceGenerator(name = "ATTACHMENT_TEMP_ID_SEQ", sequenceName = "ATTACHMENT_TEMP_ID_SEQ")
	private Long id;
	
	@Lob
	private byte[] file;

	public AttachmentTemp() {
	}
	
	public AttachmentTemp(Long id, byte[] file) {
		this.id = id;
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public byte[] getFile() {
		return file;
	}
	
}
