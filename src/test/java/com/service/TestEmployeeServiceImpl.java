package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import com.dao.MagicDaoRepository;
import com.dto.EmployeeDTO;
import com.entity.EmployeeEntity;
import com.exception.DuplicateEmailException;

@RunWith(MockitoJUnitRunner.class)
public class TestEmployeeServiceImpl {

	@Mock
	MagicDaoRepository magicDaoRepository;

	@InjectMocks
	EmployeeServiceImpl employeeServiceImpl;

	byte[] dummyByteArray = new byte[] { 1, 2, 3, 4, 5 }; // Replace this with your desired byte values

//	public void registerEmployee(EmployeeDTO employeeDTO) {
//	EmployeeEntity empEntity = new EmployeeEntity();
//	BeanUtils.copyProperties(employeeDTO, empEntity);
//	try {
//	magicDaoRepository.saveAndFlush(empEntity);
//	}
//	catch(Exception e) {
//		throw new DuplicateEmailException("Email already exist in database.");
//	}
//}

	// success
	@Test
	public void testRegisterEmployeeSuccess() {
		EmployeeDTO employeeDTO = new EmployeeDTO(1, "Bickey", "manandharbickey@gmail.com", "test@123", 120000,
				dummyByteArray);

		// mocking saveAndFlush method to succeed
		Mockito.when(magicDaoRepository.saveAndFlush(Mockito.any(EmployeeEntity.class))).thenReturn(null);

		// call the method to be tested
		employeeServiceImpl.registerEmployee(employeeDTO);

		// verify that the saveAndFlush method was called once with the correct argument
		Mockito.verify(magicDaoRepository, Mockito.times(1)).saveAndFlush(Mockito.any(EmployeeEntity.class));
	}

	// fail
	@Test(expected = DuplicateEmailException.class)
	public void testRegisterEmployeeFail() {
		EmployeeDTO employeeDTO = new EmployeeDTO(1, "Bickey", "duplicateEmail@gmail.com", "test@123", 120000,
				dummyByteArray);
		// Mocking the saveAndFlush method to throw an exception
		Mockito.doThrow(DuplicateEmailException.class).when(magicDaoRepository)
				.saveAndFlush(Mockito.any(EmployeeEntity.class));

		// call the method to be tested expecting an exception
		employeeServiceImpl.registerEmployee(employeeDTO);
	}

//	public List<EmployeeDTO> showAll() {
//		List<EmployeeEntity> employeeEntity = magicDaoRepository.findAll();
//
//		List<EmployeeDTO> employeeDtoList = new ArrayList<>(); // null
//
//		if (employeeEntity.size() > 0) {
//
//			for (EmployeeEntity tempVar : employeeEntity) {
//
//				EmployeeDTO employeeDTO = new EmployeeDTO();
//				BeanUtils.copyProperties(tempVar, employeeDTO);
//
//				employeeDtoList.add(employeeDTO);
//
//			}
//
//		}
//
//		return employeeDtoList;
//	}

	@Test
	public void testShowAll() {
		EmployeeEntity empEntity1 = new EmployeeEntity("Bickey", "Bickey@gmail.com", "test@123", 130000,
				dummyByteArray);
		EmployeeEntity empEntity2 = new EmployeeEntity("Akash", "Akash@gmail.com", "test@123", 125000, dummyByteArray);
		List<EmployeeEntity> listEmpEntity = new ArrayList<>();
		listEmpEntity.add(empEntity1);
		listEmpEntity.add(empEntity2);

		// mocking findAll() method to succeed
		Mockito.when(magicDaoRepository.findAll()).thenReturn(listEmpEntity);

		// call the method to be tested expecting and assign it to listEmpDTO
		List<EmployeeDTO> result = employeeServiceImpl.showAll();

		// Verify the expected behaviour
		assertEquals(2, result.size());

		// Verify the contents of the first employee
		// assertEquals(0, result.get(0).getEmployeeId());
		assertEquals("Bickey", result.get(0).getEmployeeName());
		assertEquals("Bickey@gmail.com", result.get(0).getEmailId());
		assertEquals("test@123", result.get(0).getPassword());
		assertEquals(130000, result.get(0).getSalary());
		assertEquals(dummyByteArray, result.get(0).getPhoto());

		// Verify the contents of the second employee
		// assertEquals(1, result.get(0).getEmployeeId());
		assertEquals("Akash", result.get(1).getEmployeeName());
		assertEquals("Akash@gmail.com", result.get(1).getEmailId());
		assertEquals("test@123", result.get(1).getPassword());
		assertEquals(125000, result.get(1).getSalary());
		assertEquals(dummyByteArray, result.get(1).getPhoto());
	}

//	@Override
//	public EmployeeDTO showOne(int employeeId) {
//		Optional<EmployeeEntity> optional= magicDaoRepository.findById(employeeId);
//		
//		if(optional.isPresent()) {
//			EmployeeEntity employeeEntity=optional.get();
//			EmployeeDTO employeeDTO = new EmployeeDTO();
//			BeanUtils.copyProperties(employeeEntity, employeeDTO);
//			return employeeDTO;
//			}	
//		return null;
//	}

	// if employeeId does not exist
	@Test
	public void testShowOneDoesNotExist() {
		EmployeeEntity mockEmployeeEntity = null;
		Mockito.when(magicDaoRepository.findById(300)).thenReturn(Optional.ofNullable(mockEmployeeEntity));
		EmployeeDTO result = employeeServiceImpl.showOne(300);
		// Verify the expected behaviour
		assertEquals(null, result); // we can also use assertNull(result)
	}

	// if employeeId exist
	@Test
	public void testShowOneExist() {
		EmployeeEntity mockEmployeeEntity = new EmployeeEntity("Bickey", "Bickey@gmail.com", "test@123", 130000,
				dummyByteArray);
		Mockito.when(magicDaoRepository.findById(1)).thenReturn(Optional.of(mockEmployeeEntity));

		EmployeeDTO result = employeeServiceImpl.showOne(1);

		assertNotNull(result);
		assertEquals("Bickey", result.getEmployeeName());
		assertEquals("Bickey@gmail.com", result.getEmailId());
		assertEquals("test@123", result.getPassword());
		assertEquals(130000, result.getSalary());
		assertEquals(dummyByteArray, result.getPhoto());
	}
}
