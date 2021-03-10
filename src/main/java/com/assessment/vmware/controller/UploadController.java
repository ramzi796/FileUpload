package com.assessment.vmware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assessment.vmware.model.ResponseObject;
import com.assessment.vmware.service.UploadService;

@RestController
@RequestMapping("/assessment")
public class UploadController {
	
	private String getUrl = "/assessment/fetchTask/";

	@Autowired
	private UploadService uploadService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile multipartFile)
			throws Exception {
		ResponseObject object = uploadService.uploadFile(multipartFile);
		if(object.getStatus().equals("Accepted")) {
			object.setUrl(getUrl + object.getTask());
		}
		return new ResponseEntity<ResponseObject>(object, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/fetchTask/{uniqueId}")
	public ResponseEntity<ResponseObject> fetchTaskInfo(
			@PathVariable(name = "uniqueId") String uniqueId) {
		ResponseObject object = uploadService.fetchBaseOnUniqueId(uniqueId);
		if(object.getStatus().equals("Found")) {
			return new ResponseEntity<ResponseObject>(object, HttpStatus.FOUND);
		} else {
			return new ResponseEntity<ResponseObject>(object, HttpStatus.NOT_FOUND);
		}
	}

}
