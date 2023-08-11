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

@RequestMapping("api/v3")
@RestController
public class FrontController3 {

	@Autowired
	EmployeeService employeeService;

	@PostMapping("/employee")
	public ResponseEntity<String> registerEmployeeP(@RequestBody EmployeeDTO employeeDTO) {
		employeeService.registerEmployee(employeeDTO);
		return new ResponseEntity<>("Registration Successful.", HttpStatus.CREATED);
	}

	@GetMapping("/employee")
	public ResponseEntity<?> showAll() {
		return new ResponseEntity<>(employeeService.showAll(), HttpStatus.OK);
	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<?> showOne(@PathVariable int employeeId){
		if(employeeId>=0) {
		if (employeeService.showOne(employeeId) != null) {
			return new ResponseEntity<>(employeeService.showOne(employeeId), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Employee with the given ID does not exist.", HttpStatus.NOT_FOUND);
		}
		}
		else {
			return new ResponseEntity<>("Employee Id cannot be negative.", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/employee/{emailId}/{password}")
	public ResponseEntity<?> logIn(@PathVariable String emailId, @PathVariable String password) {
		try {
			return new ResponseEntity<>(employeeService.logIn(emailId, password), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Email or password is incorrect or employee does not exist!!", HttpStatus.UNAUTHORIZED);
		}
	}

	@PutMapping("/employee")
	public ResponseEntity<String> editEmployee(@RequestBody EmployeeDTO employeeDTO) {
		ResponseDTO responseDTO = employeeService.editUser(employeeDTO);
		if (responseDTO.getStatus() == 200) {
			return new ResponseEntity<>(responseDTO.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<>("Edit operation failed.", HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/employee/{employeeId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable int employeeId){
		// if the id does not exist it will throw some exception so we need to handle it
		if(employeeId>=0) {
		try {
			return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Delete operation failed. Employee with the given ID does not exist.",
					HttpStatus.BAD_REQUEST);
		}
		}
		else {
			return new ResponseEntity<>("Employee Id cannot be negative.", HttpStatus.BAD_REQUEST);
		}
	}


}
