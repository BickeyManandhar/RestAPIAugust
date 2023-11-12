package com.controllers;

import java.nio.charset.StandardCharsets;
import org.apache.tomcat.util.codec.binary.Base64;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.dto.EmployeeDTO;
import com.dto.ResponseDTO;
import com.service.EmployeeService;
import com.utils.TestUtil;

public class TestFrontController3 {

	// initial setup for testing starts here

	// class we are mocking which is giving us data
	@Mock
	private EmployeeService employeeService;

	// class we are testing
	@InjectMocks
	private FrontController3 forntController3;

	// this will work as our client side just like postman
	private MockMvc mockMvc;
	
	byte[] dummyByteArray = new byte[]{1, 2, 3, 4, 5}; // Replace this with your desired byte values

	@Before
	public void anything() {

		// MockitoAnnotations.openMocks(this);
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(forntController3).build();

	}

	// initial setup for testing ends here

	// We are testing this method inside the controller
//	@GetMapping("/employee")
//	public ResponseEntity<?> showAll() {
//		return new ResponseEntity<>(employeeService.showAll(), HttpStatus.OK);
//	}

	@Test
	public void testShowAll() throws Exception {
		List<EmployeeDTO> employeeDTOList = new ArrayList<>();
		EmployeeDTO employeeDTO1 = new EmployeeDTO(1, "Bickey", "manandharbikcey@gmail.com", "test@123", 120000,dummyByteArray);
		EmployeeDTO employeeDTO2 = new EmployeeDTO(2, "Akash", "akash@gmail.com", "testing@123", 100000,dummyByteArray);
		employeeDTOList.add(employeeDTO1);
		employeeDTOList.add(employeeDTO2);
		Mockito.when(employeeService.showAll()).thenReturn(employeeDTOList);

		// same like sending data from postman
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v3/employee").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].employeeId").value(1))
				.andExpect(jsonPath("$[0].employeeName").value("Bickey"))
				.andExpect(jsonPath("$[0].emailId").value("manandharbikcey@gmail.com"))
				.andExpect(jsonPath("$[0].password").value("test@123")).andExpect(jsonPath("$[0].salary").value(120000))
				.andExpect(jsonPath("$[0].photo").value(Base64.encodeBase64String(dummyByteArray))) // Convert to Base64
				// .andExpect(MockMvcResultMatchers.status().is(200))

				.andExpect(jsonPath("$[1].employeeId").value(2)).andExpect(jsonPath("$[1].employeeName").value("Akash"))
				.andExpect(jsonPath("$[1].emailId").value("akash@gmail.com"))
				.andExpect(jsonPath("$[1].password").value("testing@123"))
				.andExpect(jsonPath("$[1].salary").value(100000)).andDo(print())
				.andExpect(jsonPath("$[1].photo").value(Base64.encodeBase64String(dummyByteArray)));

	}

	// We are testing this method inside the controller
//	@PostMapping("/employee")
//	public ResponseEntity<String> registerEmployeep(@RequestBody EmployeeDTO employeeDTO) {
//
//		employeeService.registerEmployee(employeeDTO);
//		return new ResponseEntity<>("Registration Successful.", HttpStatus.CREATED);
//	}

	@Test
	public void testRegisterEmployee() throws Exception {
		EmployeeDTO employeeDTO = new EmployeeDTO(3, "Babi", "babi@gmail.com", "test", 110000,dummyByteArray);

		// the method employeeService.registerEmployee(employeeDTO) does not return
		// anything, hence we used doNothing()
		Mockito.doNothing().when(employeeService).registerEmployee(employeeDTO);

		// when we hit from postman, we are giving JSON object, but the method is taking
		// Java object, so we are creating TestUtil class that converts JSON to Java
		// object
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v3/employee")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8)
				.content(TestUtil.convertObjectToJsonBytes(employeeDTO))
				.accept(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8))

				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().string("Registration Successful.")).andDo(print());
		verify(employeeService, times(1)).registerEmployee(employeeDTO);// must override hashcode and equals(), object
																		// equality is determined by the equals() method
		verifyNoMoreInteractions(employeeService);
	}

//	@GetMapping("/employee/{employeeId}")
//	public ResponseEntity<?> showOne(@PathVariable int employeeId) {
//		if (employeeId >= 0) {
//			if (employeeService.showOne(employeeId) != null) {
//				return new ResponseEntity<>(employeeService.showOne(employeeId), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<>("Employee with the given ID does not exist.", HttpStatus.NOT_FOUND);
//			}
//		} else {
//			return new ResponseEntity<>("Employee Id cannot be negative.", HttpStatus.BAD_REQUEST);
//		}
//	}

	// fail 1: test case for Negative employeeId
	@Test
	public void testShowOneNegativeId() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v3/employee/{employeeId}", -2))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().string("Employee Id cannot be negative.")).andDo(print());

	}

	// fail 2: test case for employeeId that does not exist
	@Test
	public void testShowOneDoesNotExist() throws Exception {
		Mockito.when(employeeService.showOne(300)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v3/employee/{employeeId}", 300))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("Employee with the given ID does not exist."))
				.andDo(print()).andReturn();
	}

	// success: test case with positive employeeId and Id exist in DB
	@Test
	public void testShowOneSuccess() throws Exception {
		EmployeeDTO mockEmployeeDTO = new EmployeeDTO(2, "Bickey", "manandharbickey@gmail.com", "test@123", 120000,dummyByteArray);
		Mockito.when(employeeService.showOne(2)).thenReturn(mockEmployeeDTO);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v3/employee/{employeeId}", 2))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$.employeeId").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.employeeName").value("Bickey"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.emailId").value("manandharbickey@gmail.com"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.password").value("test@123"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.photo").value(Base64.encodeBase64String(dummyByteArray)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(120000)).andReturn();

	}

//	@PutMapping("/employee")
//	public ResponseEntity<String> editEmployee(@RequestBody EmployeeDTO employeeDTO) {
//		ResponseDTO responseDTO = employeeService.editUser(employeeDTO);
//		if (responseDTO.getStatus() == 200) {
//			return new ResponseEntity<>(responseDTO.getMessage(), HttpStatus.OK);
//		}
//		return new ResponseEntity<>("Edit operation failed.", HttpStatus.BAD_REQUEST);
//
//	}

	@Test
	public void testEditEmployeeSuccess() throws Exception{
		EmployeeDTO mockEmployeeDTO = new EmployeeDTO(2, "Bickey", "manandharbickey@gmail.com", "test@123", 120000,dummyByteArray);
		ResponseDTO mockResponseDTO = new ResponseDTO("Edit Success", 201);
		//Mockito.when(employeeService.editUser(mockEmployeeDTO)).thenReturn(mockResponseDTO);
		Mockito.when(employeeService.editUser(Mockito.any(EmployeeDTO.class))).thenReturn(mockResponseDTO);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v3/employee")
				.contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8)
				.content(TestUtil.convertObjectToJsonBytes(mockEmployeeDTO))
				.accept(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8))
				.andExpect(MockMvcResultMatchers.status().isCreated());
				//.andExpect(MockMvcResultMatchers.content().string("Registration Successful.")).andDo(print());
		verify(employeeService, times(1)).registerEmployee(mockEmployeeDTO);// must override hashcode and equals(), object
																		// equality is determined by the equals() method
		verifyNoMoreInteractions(employeeService);

	}

}
