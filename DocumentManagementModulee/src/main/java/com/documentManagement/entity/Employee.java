package com.documentManagement.entity;

import jakarta.persistence.*;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@Data
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	
	private String lastName;

	@Column(unique = true, nullable = false)
	private String email; // Unique constraint for email
	
	private Long employeeId;
	
	@JsonIgnore
	private String password;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	private String gender;

	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	private String address;

	@Column(name = "job_title", nullable = false)
	private String jobTitle;

	private String role;

	@Column(name = "date_of_joining", nullable = false)
	private LocalDate dateOfJoining;

	@Column(name = "employee_type", nullable = false)
	private String employeeType; // Full-Time, Part-Time, Contract

	private String status; // Active, Inactive
	
	private String department;

}