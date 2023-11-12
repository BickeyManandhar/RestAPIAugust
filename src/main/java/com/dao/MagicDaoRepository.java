package com.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.entity.EmployeeEntity;

public interface MagicDaoRepository extends JpaRepository<EmployeeEntity,Integer>{

	EmployeeEntity findByEmailIdAndPassword(String emailId,String password);
	
	@Query("SELECT e FROM EmployeeEntity e WHERE e.employeeName LIKE %:n%")
	public List<EmployeeEntity> searchByEmployeeName(@Param("n") String name);
	
	@Query("SELECT e FROM EmployeeEntity e WHERE e.salary >= :minSalary AND e.salary <= :maxSalary")
	public List<EmployeeEntity> searchBySalaryRange(@Param("minSalary") int minSalary, @Param("maxSalary") int maxSalary);

	List<EmployeeEntity> findByEmailId(String emailId);

	
}
