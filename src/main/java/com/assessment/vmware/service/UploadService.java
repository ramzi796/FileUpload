package com.assessment.vmware.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.vmware.events.FileEvent;
import com.assessment.vmware.model.Employee;
import com.assessment.vmware.model.ResponseObject;
import com.assessment.vmware.repo.EmployeeRepository;

@Service
public class UploadService implements ApplicationEventPublisherAware {
	
	@Autowired
	private EmployeeRepository repo;
	
	private ApplicationEventPublisher publisher;
	
	public ResponseObject uploadFile(MultipartFile file) throws Exception {
		ResponseObject object = new ResponseObject();
		if(file != null) {
			if(getExtensionByStringHandling(file.getOriginalFilename()).get().equalsIgnoreCase("txt")) {
				String uniqueId = UUID.randomUUID().toString();
				publisher.publishEvent(new FileEvent(this, file, uniqueId));
				
				object.setTask(uniqueId);
				object.setStatus("Accepted");
			} else {
				object.setTask(null);
				object.setStatus("Failure");
				object.setErrMessage("Only txt extension is accepted");
			}
		} else {
			object.setTask(null);
			object.setStatus("Failure");
			object.setErrMessage("File is null");
		}
		return object;
	}
	
	public ResponseObject fetchBaseOnUniqueId(String uniqueId) {
		List<Employee> employees =  repo.fetchEmployeedByUniqueId(uniqueId);
		ResponseObject object = new ResponseObject();
		if(employees != null && !employees.isEmpty() ) {
			object.setTask(uniqueId);
			object.setStatus("Found");
			object.setEmployees(employees);
		} else {
			object.setTask(uniqueId);
			object.setStatus("Not Found");
		}
		return object;
	}
	
	private Optional<String> getExtensionByStringHandling(String filename) {
	    return Optional.ofNullable(filename)
	      .filter(f -> f.contains("."))
	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
