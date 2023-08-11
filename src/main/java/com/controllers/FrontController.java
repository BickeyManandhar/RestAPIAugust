package com.controllers;

import java.util.List;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dto.EmployeeDTO;
import com.dto.ResponseDTO;
import com.service.EmployeeService;

@RequestMapping("api/v1")
@RestController
public class FrontController {
	

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/register")
	public ResponseDTO registerEmployeeP(@RequestBody EmployeeDTO employeeDTO) {
		employeeService.registerEmployee(employeeDTO);
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setMessage("Registration Successful.");
		responseDTO.setStatus(201);
		return responseDTO;
	}
	
	@GetMapping("/allUsers")
	public List<EmployeeDTO> showAll(){
		return employeeService.showAll();
	}
	
	@GetMapping("/allUsers/{employeeId}")
	public EmployeeDTO showOne(@PathVariable int employeeId){
		
		return employeeService.showOne(employeeId);
	}
	
	@PostMapping("/signIn/{emailId}/{password}")
	public EmployeeDTO logIn(@PathVariable String emailId, @PathVariable String password) {
		return employeeService.logIn(emailId,password);
	}
	
	@PutMapping("/editEmployee")
	public ResponseDTO editEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.editUser(employeeDTO);
	}
	
	@DeleteMapping("/allUsers/{employeeId}")
	public List<EmployeeDTO> deleteEmployee(@PathVariable int employeeId){
		return employeeService.deleteEmployee(employeeId);
	}
	
	
//	@PutMapping("/editEmployee/{employeeId}")
//	public EmployeeDTO editEmployee(@RequestBody EmployeeDTO employeeDTO,@PathVariable int employeeId) {
//		employeeService.editUser(employeeDTO,employeeId);
//		return employeeDTO;
//	}

}
