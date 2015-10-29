package com.scaffy.controller;

public class AttachmentException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public AttachmentException(Throwable e) {
		super("Error while processing attachments", e);
	}

}
