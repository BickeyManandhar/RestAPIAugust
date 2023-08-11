package com.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dto.EmployeeDTO;
import com.entity.EmployeeEntity;

public interface MagicDaoRepository extends JpaRepository<EmployeeEntity,Integer>{

	EmployeeEntity findByEmailIdAndPassword(String emailId,String password);

//	Optional<EmployeeEntity> findByEmailIdAndPassword(String emailId, String password);
//
//	List<EmployeeEntity> findAll();  //optional
//
//	void deleteByEmailId(String emailId);
//
//	EmployeeEntity findAllByEmailId(String emailId);
	
	
}
