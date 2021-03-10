package com.assessment.vmware.events;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.assessment.vmware.model.Employee;
import com.assessment.vmware.repo.EmployeeRepository;

@Component
public class FileEventListener {
	
	@Autowired
	private EmployeeRepository repo;
	
	@EventListener
	public void processEvents(FileEvent event) {
		File fileTocreate = null;
		BufferedReader br  = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			String filePath = classLoader.getResource(".").getFile() + event.getFile().getOriginalFilename();
			fileTocreate = new File(filePath);
			event.getFile().transferTo(fileTocreate);
			boolean flag = true;
			
			br = new BufferedReader(new FileReader(filePath));
			String st; 
			while ((st = br.readLine()) != null) {
			   String[] data = st.split(" ");
			   String name = "";
			   int age = 0;
			   if(data.length > 2) {
				   for(int i=0; i<(data.length - 1); i++) {
					   name = name + data[i];
				   }
				   age = Integer.parseInt(data[data.length - 1]);
			   } else if(data.length == 2) {
				   name = data[0];
				   age = Integer.parseInt(data[1]);
			   } else {
				   flag = false;
			   }
			   if(flag) {
				   Employee emp = new Employee(name, age, event.getUniqueId());
				   repo.save(emp);
			   }
			}
		} catch(Exception ex) {
			System.out.println("Please check the data in the file!");
		} finally {
			if(fileTocreate != null) {
				fileTocreate.deleteOnExit();
			}
		}
	}

}
