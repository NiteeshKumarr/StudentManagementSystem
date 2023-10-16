package com.jsp.studentmanagmentsystem.controller;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.studentmanagmentsystem.dto.MessageData;
import com.jsp.studentmanagmentsystem.dto.StudentRequest;
import com.jsp.studentmanagmentsystem.dto.StudentResponse;
import com.jsp.studentmanagmentsystem.entity.Student;
import com.jsp.studentmanagmentsystem.service.StudentService;
import com.jsp.studentmanagmentsystem.util.ResponseStructure;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService service;
	
	
	
	@PostMapping("/students")
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(@RequestBody StudentRequest studentRequest){
		
		
		return service.saveStudent(studentRequest);
	}
	@PutMapping("/update")
	
	public ResponseEntity<ResponseStructure<StudentResponse>> update(
		@RequestBody Student student,int studentId){
         
		return service.updateStudent(student, studentId);
		
	}
	@DeleteMapping("/{studentId}")
	
	public ResponseEntity<ResponseStructure<StudentResponse>> delete(@PathVariable int studentId){
		return service.deleteStudent(studentId);
	}
	
	@GetMapping(params = "email")
	public ResponseEntity<StudentResponse> findByEmail(@RequestParam String email){
		return service.findByEmail(email);
	}
	
	@GetMapping("/find")
	public ResponseEntity<ResponseStructure<StudentResponse>> findById(@RequestParam int studentId){
		return service.findStudentById(studentId);
	}
	
	
	@PostMapping("/extract")
	public ResponseEntity<String> extractDataFromFile(@RequestParam MultipartFile file)
	throws IOException{
		return service.extractDataFromExcel(file);
		
	}
	
	@PostMapping("/write/excel")
	public ResponseEntity<String> writeToExcel(@RequestParam String filePath) throws IOException{
		return service.writeToExcel(filePath);
	}
	
	@GetMapping("/mail")
	public ResponseEntity<String> sendMail(HttpServletRequest req,HttpServletResponse res, MessageData messageData){
		String to=req.getParameter("to");
		String subject=req.getParameter("subject");
		String text = req.getParameter("text");
		return service.sendMail(messageData);
	}
	
	@PostMapping("/extract/csv")
	public ResponseEntity<String> extractDataFromCsv(@RequestParam MultipartFile file)throws IOException{
		return service.extractDataFromCsv(file);
	}
	
	
	
	
	
	

}
