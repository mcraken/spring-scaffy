/**
 * 
 */
package com.scaffy.service;

import java.util.List;

import com.scaffy.controller.MultipartRequest.Attachment;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public interface AttachmentService {
	
	public void createAttachments(List<Attachment> attachments);
}
