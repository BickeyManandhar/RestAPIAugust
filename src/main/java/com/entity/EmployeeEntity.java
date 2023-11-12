package com.entity;

import javax.persistence.Transient;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;



@Entity
@Table(name = "emp_tbl")
public class EmployeeEntity {
	
	int employeeId;
	String employeeName;
	String emailId;
	String password;
	int salary;
	//String base64Photo; we need this only in dto part not needed in DB
	MultipartFile file;

	byte[] tempPhoto;
	
	public EmployeeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EmployeeEntity(int employeeId, String employeeName, String emailId, String password, int salary,
			MultipartFile file, byte[] tempPhoto) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.emailId = emailId;
		this.password = password;
		this.salary = salary;
		this.file = file;
		this.tempPhoto = tempPhoto;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Transient //if we do not want this column to be created in DB
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public byte[] getTempPhoto() {
		return tempPhoto;
	}
	public void setTempPhoto(byte[] tempPhoto) {
		this.tempPhoto = tempPhoto;
	}
	@Override
	public String toString() {
		return "EmployeeEntity [employeeId=" + employeeId + ", employeeName=" + employeeName + ", emailId=" + emailId
				+ ", password=" + password + ", salary=" + salary + ", file=" + file + ", tempPhoto="
				+ Arrays.toString(tempPhoto) + "]";
	}
	
	

	
}
