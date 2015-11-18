/**
 * 
 */
package com.scaffy.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.scaffy.entity.attachment.Attachment;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public class MultipartResponse {

	private List<Attachment> attachments;

	private Object model;

	private String targetName;

	private String resourceId;
	
	private Attachment attachment;

	public MultipartResponse(Object model, MultipartHttpServletRequest request) throws AttachmentException {
		
		try {
			
			this.model = model;
			
			Attachable attachable = model.getClass().getAnnotation(Attachable.class);

			this.targetName = attachable.targetName();

			this.resourceId = PropertyUtils.getProperty(model, attachable.uniqueId()).toString();
			
			buildAttachmentList(request);
			
		} catch (Exception e) {
			
			throw new AttachmentException(e);
		} 
		
	}
	
	public MultipartResponse(Attachment attachment) {
		
		this.attachment = attachment;
	}

	private void buildAttachmentList(MultipartHttpServletRequest request)
			throws IOException {

		MultipartFile file;

		Iterator<String> fileNames =  request.getFileNames();

		attachments = new ArrayList<Attachment>();

		while(fileNames.hasNext()){

			file = request.getFile(fileNames.next());

			attachments.add(
					new Attachment(
							file.getOriginalFilename(), 
							file.getContentType(),
							file.getBytes(),
							targetName,
							resourceId
							)
					);
		}
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	@SuppressWarnings("unchecked")
	public <T>T getModel() {
		return (T)model;
	}
	
	public String getTargetName() {
		return targetName;
	}
	
	public String getResourceId() {
		return resourceId;
	}
	
	public void sendAttachment(HttpServletResponse response) throws AttachmentException {
		
		try {
			response.setContentType(attachment.getContentType());

			response.setContentLength(attachment.getDataFile().length);

			BufferedOutputStream stream = new BufferedOutputStream(response.getOutputStream());
			
			stream.write(attachment.getDataFile());

			stream.close();
			
		} catch (IOException e) {
			
			throw new AttachmentException(e);
		}
	}

}
