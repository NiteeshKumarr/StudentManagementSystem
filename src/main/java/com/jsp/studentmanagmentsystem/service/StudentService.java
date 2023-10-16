package com.jsp.studentmanagmentsystem.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.studentmanagmentsystem.dto.MessageData;
import com.jsp.studentmanagmentsystem.dto.StudentRequest;
import com.jsp.studentmanagmentsystem.dto.StudentResponse;
import com.jsp.studentmanagmentsystem.entity.Student;
import com.jsp.studentmanagmentsystem.util.ResponseStructure;

import jakarta.mail.MessagingException;

public interface StudentService {
	
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest studentRequest);
	
	
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(Student student,int studentId);
	
	
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId);
	
	
	public ResponseEntity<ResponseStructure<StudentResponse>> findStudentById(int studentId);
	
	
	public ResponseEntity<List<Student>> findAllStudents();
	
	public ResponseEntity<StudentResponse> findByEmail(String studentEmail);
	
	public ResponseEntity<StudentResponse> findByPhNo(long phone);
	
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file) throws IOException;
	
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException;
	
	public ResponseEntity<String> sendMail(MessageData messageData);
	
	public ResponseEntity<String> sendMailMessage(MessageData data) throws MessagingException;
	
	public ResponseEntity<String> extractDataFromCsv(MultipartFile file) throws IOException;

}
