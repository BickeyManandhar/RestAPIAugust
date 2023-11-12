package com.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dto.EmployeeDTO;
import com.dto.ResponseDTO;
import com.exception.UserDoesNotExist;
import com.service.EmployeeService;

@RequestMapping("/api/v4")
@Controller
public class FrontController4 {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/employee")
	public String showAll(Model model) {
		List<EmployeeDTO> listEmployeeDTO = employeeService.showAll();
		List<EmployeeDTO> listOfEmployeeDTOWithPhoto = new ArrayList<>();
		for (EmployeeDTO empDTO : listEmployeeDTO) {
			byte[] photoInByteArray = empDTO.getTempPhoto();
			if (empDTO.getTempPhoto() != null) {
				byte[] photoEncodedInBase64 = Base64.getEncoder().encode(photoInByteArray);

				try {
					String photoEncodedInBase64String = new String(photoEncodedInBase64, "UTF-8");
					empDTO.setPhoto(photoEncodedInBase64String);
					listOfEmployeeDTOWithPhoto.add(empDTO);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("listEmployeeDTO", listOfEmployeeDTOWithPhoto);
		return "employee";

	}

	@GetMapping("/registerEmployeePage")
	public String registerEmployeePage(Model model) {
		return "employeeRegistrationPage";
	}

	@PostMapping("/employee")
	public String registerEmployee(@ModelAttribute EmployeeDTO employeeDTO, HttpServletRequest httpServletRequest) {
		// RequestBody if we are sending JSON data from postman
		// ModelAttribute if we are working with java classes (DTO) within out project
		employeeService.registerEmployee(employeeDTO);
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("registrationSuccess", "Registration Successful.");
		return "redirect:/api/v4/employee";
	}

	// use @Query in dao
	@GetMapping("/employee/searchByName/{employeeName}")
	public ResponseEntity<?> searchByEmployeeName(@PathVariable String employeeName) {
		if (employeeService.searchByEmployeeName(employeeName) != null
				&& !employeeService.searchByEmployeeName(employeeName).isEmpty()) {
			return new ResponseEntity<>(employeeService.searchByEmployeeName(employeeName), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No employee with provided name is found.", HttpStatus.NOT_FOUND);
		}

	}

	// use @Query in dao
	@GetMapping("/employee/searchBySalary/{minSalary}/{maxSalary}")
	public ResponseEntity<?> searchBySalaryRange(@PathVariable int minSalary, @PathVariable int maxSalary) {
		if (employeeService.searchBySalaryRange(minSalary, maxSalary) != null
				&& !employeeService.searchBySalaryRange(minSalary, maxSalary).isEmpty()) {
			return new ResponseEntity<>(employeeService.searchBySalaryRange(minSalary, maxSalary), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No employee with provided salary is found.", HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/employee/{employeeId}")
	public ResponseEntity<?> showOne(@PathVariable int employeeId) {
		if (employeeId >= 0) {
			if (employeeService.showOne(employeeId) != null) {
				return new ResponseEntity<>(employeeService.showOne(employeeId), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Employee with the given ID does not exist.", HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("Employee Id cannot be negative.", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/employee/{emailId}/{password}")
	public ResponseEntity<?> logIn(@PathVariable String emailId, @PathVariable String password) {
		try {
			return new ResponseEntity<>(employeeService.logIn(emailId, password), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Email or password is incorrect or employee does not exist!!",
					HttpStatus.UNAUTHORIZED);
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
	public ResponseEntity<?> deleteEmployee(@PathVariable int employeeId) {

		// if the id does not exist it will throw some exception so we need to handle it
		if (employeeId >= 0) {
			try {
				return new ResponseEntity<>(employeeService.deleteEmployee(employeeId), HttpStatus.OK);
			} catch (Exception e) {
				throw new UserDoesNotExist("Delete operation failed. Employee with the given ID does not exist.");
			}
		} else {
			return new ResponseEntity<>("Employee Id cannot be negative.", HttpStatus.BAD_REQUEST);
		}
	}
}
