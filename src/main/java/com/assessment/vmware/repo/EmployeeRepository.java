package com.assessment.vmware.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.assessment.vmware.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	@Query("SELECT emp FROM Employee emp WHERE emp.uniqueId = ?1")
	public List<Employee> fetchEmployeedByUniqueId(String uniqueId);
}
