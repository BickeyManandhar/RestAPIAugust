package com.service;

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

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	MagicDaoRepository magicDaoRepository;

	@Override
	public void registerEmployee(EmployeeDTO employeeDTO) {
		EmployeeEntity empEntity = new EmployeeEntity();
		BeanUtils.copyProperties(employeeDTO, empEntity);
		magicDaoRepository.save(empEntity);
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

//	@Override
//	public EmployeeDTO editUser(EmployeeDTO employeeDTO, int employeeId) {
//		 if (magicDaoRepository.existsById(employeeId)) {
//		        Optional<EmployeeEntity> optionalEmployeeEntity = magicDaoRepository.findById(employeeId);
//		        if (optionalEmployeeEntity.isPresent()) {
//		            EmployeeEntity employeeEntity = optionalEmployeeEntity.get();
//		            // Copy properties from employeeDTO to the existing employeeEntity
//		            BeanUtils.copyProperties(employeeDTO, employeeEntity);
//		            employeeEntity = magicDaoRepository.save(employeeEntity);
//		        }
//		    }
//		    return employeeDTO;
//		
//	}

}
