/**
 * 
 */
package com.scaffy.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * @author 	Sherief Shawky
 * @Email 	mcrakens@gmail.com
 */
public class MultipartRequest {
	
	public class Attachment {
		
		public byte[] file;

		public String originalFileName;

		public String contentType;
		
		public Attachment(byte[] file, String originalFileName,
				String contentType) {

			this.file = file;
			this.originalFileName = originalFileName;
			this.contentType = contentType;
		}
	}
	
	private List<Attachment> attachments;
	
	private Object model;
	
	public MultipartRequest(Object model, MultipartHttpServletRequest request) throws IOException {
		
		this.model = model;
		
		buildAttachmentList(request);
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
							file.getBytes(), 
							file.getOriginalFilename(), 
							file.getContentType()
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
	
}
