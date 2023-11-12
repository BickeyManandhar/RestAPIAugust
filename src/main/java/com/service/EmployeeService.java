package com.service;

import java.util.List;

import com.dto.EmployeeDTO;
import com.dto.ResponseDTO;
import com.entity.EmployeeEntity;

public interface EmployeeService {



	void registerEmployee(EmployeeDTO employeeDTO);

	List<EmployeeDTO> showAll();

	EmployeeDTO logIn(String emailId, String password);
	
	ResponseDTO editUser(EmployeeDTO employeeDTO);

	EmployeeDTO showOne(int employeeId);

	List<EmployeeDTO> deleteEmployee(int employeeId);

	//EmployeeDTO editUser(EmployeeDTO employeeDTO, int employeeId);
	
	List<EmployeeDTO> searchByEmployeeName(String name);
	
	List<EmployeeDTO> searchBySalaryRange(int minSalary, int maxSalary);



}
