package com.jsp.studentmanagmentsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.studentmanagmentsystem.entity.Student;

public interface StudentRepo extends JpaRepository<Student, Integer>{
	
	public Student findByStudentEmail(String email);
	
	public Student findByStudentPhNo(long phNo);
	

}
