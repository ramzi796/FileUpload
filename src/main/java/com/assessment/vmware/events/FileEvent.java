package com.assessment.vmware.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

public class FileEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	
	private MultipartFile file;
	
	private String uniqueId;

	public FileEvent(Object source, MultipartFile file, String uniqueId) {
		super(source);
		this.file = file;
		this.uniqueId = uniqueId;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
