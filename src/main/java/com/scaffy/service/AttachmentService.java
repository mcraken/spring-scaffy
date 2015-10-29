/**
 * 
 */
package com.scaffy.service;

import com.scaffy.controller.MultipartResponse;
import com.scaffy.entity.attachment.Attachment;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public interface AttachmentService {
	
	public void createAttachments(MultipartResponse request);
	
	public void readAttachment(Attachment attachment);
}
