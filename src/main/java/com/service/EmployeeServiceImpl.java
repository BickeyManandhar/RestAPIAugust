package com.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MagicDaoRepository;
import com.dto.EmployeeDTO;
import com.dto.ResponseDTO;
import com.entity.EmployeeEntity;
import com.exception.DuplicateEmailException;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	MagicDaoRepository magicDaoRepository;

//	@Override
//	public void registerEmployee(EmployeeDTO employeeDTO) {
//		EmployeeEntity empEntity = new EmployeeEntity();
//		BeanUtils.copyProperties(employeeDTO, empEntity);
//		try {
//			//this is for image
//			//we are setting tempPhoto using multipart file(converted into bytes)
//			if(empEntity.getFile()!=null) {
//			empEntity.setTempPhoto(empEntity.getFile().getBytes());
//			}
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
//		try {
//		magicDaoRepository.saveAndFlush(empEntity);
//		//magicDaoRepository.save(empEntity);
//		}
//		catch(Exception e) {
//			throw new DuplicateEmailException("Email already exist in database.");
//		}
//	}

	@Override
	@Transactional
	public void registerEmployee(EmployeeDTO employeeDTO) {
	    // Check if an employee with the same email already exists
		List<EmployeeEntity> existingEmployees = magicDaoRepository.findByEmailId(employeeDTO.getEmailId());
	    
	    if (!existingEmployees.isEmpty()) {
	        throw new DuplicateEmailException("Email already exists in the database.");
	    }

	    else {
	    // If not, proceed with saving the new employee
	    EmployeeEntity empEntity = new EmployeeEntity();
	    BeanUtils.copyProperties(employeeDTO, empEntity);
	    try {
	        // This is for image
	        // We are setting tempPhoto using multipart file (converted into bytes)
	        if (empEntity.getFile() != null) {
	            empEntity.setTempPhoto(empEntity.getFile().getBytes());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    try {
	        magicDaoRepository.saveAndFlush(empEntity);
	    } catch (Exception e) {
	    	//throw new RegistrationFailedException("Registration failed due to an internal error.");
	    }
	    }
	}

	@Override
	public List<EmployeeDTO> showAll() {
		List<EmployeeEntity> employeeEntity = magicDaoRepository.findAll();

		List<EmployeeDTO> employeeDtoList = new ArrayList<>(); // null

		if (employeeEntity.size() > 0) {

			for (EmployeeEntity tempVar : employeeEntity) {

				EmployeeDTO employeeDTO = new EmployeeDTO();
				BeanUtils.copyProperties(tempVar, employeeDTO);

				employeeDtoList.add(employeeDTO);

			}

		}

		return employeeDtoList;
	}

	@Override
	public EmployeeDTO logIn(String emailId, String password) {
		EmployeeEntity employeeEntity = magicDaoRepository.findByEmailIdAndPassword(emailId, password);
		EmployeeDTO employeeDTO = new EmployeeDTO();
		BeanUtils.copyProperties(employeeEntity, employeeDTO);
		return (employeeDTO);
	}

	@Override
	public ResponseDTO editUser(EmployeeDTO employeeDTO) {
		EmployeeEntity employeeEntity = new EmployeeEntity();
		BeanUtils.copyProperties(employeeDTO, employeeEntity);
		if(magicDaoRepository.existsById(employeeEntity.getEmployeeId())) {
			magicDaoRepository.save(employeeEntity);
			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setMessage("Record updated Successfully");
			responseDTO.setStatus(200);
			return responseDTO;
		}
		else {
			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setMessage("Record updating failed. Provided employee id does not exist.");
			responseDTO.setStatus(404);
			return responseDTO;
		}
		

	}

	@Override
	public EmployeeDTO showOne(int employeeId) {
		Optional<EmployeeEntity> optional= magicDaoRepository.findById(employeeId);
		
		if(optional.isPresent()) {
			EmployeeEntity employeeEntity=optional.get();
			EmployeeDTO employeeDTO = new EmployeeDTO();
			BeanUtils.copyProperties(employeeEntity, employeeDTO);
			return employeeDTO;
			}	
		return null;
	}

	@Override
	public List<EmployeeDTO> deleteEmployee(int employeeId) {
		magicDaoRepository.deleteById(employeeId);
		List<EmployeeEntity> employeeEntity = magicDaoRepository.findAll();

		List<EmployeeDTO> employeeDtoList = new ArrayList<>(); // null

		if (employeeEntity.size() > 0) {

			for (EmployeeEntity tempVar : employeeEntity) {

				EmployeeDTO employeeDTO = new EmployeeDTO();
				BeanUtils.copyProperties(tempVar, employeeDTO);

				employeeDtoList.add(employeeDTO);

			}

		}

		return employeeDtoList;
	}

	@Override
	public List<EmployeeDTO> searchByEmployeeName(String name) {
		List<EmployeeEntity> employeeEntityList = magicDaoRepository.searchByEmployeeName(name);
		List<EmployeeDTO> employeeDtoList = new ArrayList<>();
		if (employeeEntityList.size() > 0) {

			for (EmployeeEntity tempVar : employeeEntityList) {

				EmployeeDTO employeeDTO = new EmployeeDTO();
				BeanUtils.copyProperties(tempVar, employeeDTO);

				employeeDtoList.add(employeeDTO);

			}

		}
		return employeeDtoList;
	}

	@Override
	public List<EmployeeDTO> searchBySalaryRange(int minSalary, int maxSalary) {
		List<EmployeeEntity> employeeEntityList = magicDaoRepository.searchBySalaryRange(minSalary, maxSalary);
		List<EmployeeDTO> employeeDtoList = new ArrayList<>();
		if (employeeEntityList.size() > 0) {

			for (EmployeeEntity tempVar : employeeEntityList) {

				EmployeeDTO employeeDTO = new EmployeeDTO();
				BeanUtils.copyProperties(tempVar, employeeDTO);

				employeeDtoList.add(employeeDTO);

			}

		}
		return employeeDtoList;
	}


}
