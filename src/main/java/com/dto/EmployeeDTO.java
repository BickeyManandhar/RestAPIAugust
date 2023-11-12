package com.dto;

import java.util.Arrays;

import java.util.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;



public class EmployeeDTO {
	int employeeId;

	@NotEmpty // means it cannot be empty but it will take input will only blank space as well. (example: "  ")
	String employeeName;

	@NotBlank(message = "Cannot be blank.") // means it will not take input as only blank spaces (example just 2 blank
											// spaces)
	@Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$",message="Invalid email format or empty")
	String emailId;
	
	@NotEmpty
	@Size(min=4,message="Password should be at least 4 characters long.")
	String password;
	
	@Min(10)
	int salary;
	byte[] tempPhoto;
	String photo; // we have this attribute in DTO but not in Entity.
	MultipartFile file;

	public EmployeeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmployeeDTO(int employeeId, String employeeName, String emailId, String password, int salary,
			byte[] tempPhoto, String photo, MultipartFile file) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.emailId = emailId;
		this.password = password;
		this.salary = salary;
		this.tempPhoto = tempPhoto;
		this.photo = photo;
		this.file = file;
	}

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

	public byte[] getTempPhoto() {
		return tempPhoto;
	}

	public void setTempPhoto(byte[] tempPhoto) {
		this.tempPhoto = tempPhoto;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "EmployeeDTO [employeeId=" + employeeId + ", employeeName=" + employeeName + ", emailId=" + emailId
				+ ", password=" + password + ", salary=" + salary + ", tempPhoto=" + Arrays.toString(tempPhoto)
				+ ", photo=" + photo + ", file=" + file + "]";
	}

}