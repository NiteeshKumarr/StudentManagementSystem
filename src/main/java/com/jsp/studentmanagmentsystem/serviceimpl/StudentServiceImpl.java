package com.jsp.studentmanagmentsystem.serviceimpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.jsp.studentmanagmentsystem.dto.MessageData;
import com.jsp.studentmanagmentsystem.dto.StudentRequest;
import com.jsp.studentmanagmentsystem.dto.StudentResponse;
import com.jsp.studentmanagmentsystem.entity.Student;
import com.jsp.studentmanagmentsystem.repository.StudentRepo;
import com.jsp.studentmanagmentsystem.service.StudentService;
import com.jsp.studentmanagmentsystem.util.ResponseStructure;

import jakarta.mail.MessagingException;


@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepo repo;
	
	@Autowired
	JavaMailSender javaMailSender;

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> saveStudent(StudentRequest studentRequest) {
		Student student=new Student();
		ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
		student.setStudentName(studentRequest.getStudentName());
		student.setStudentEmail(studentRequest.getStudentEmail());
		student.setStudentGrade(studentRequest.getStudentGrade());
		student.setStudentPhNo(studentRequest.getStudentPhNo());
		student.setStudentPassword(studentRequest.getStudentPassword());
		
		Student student2=repo.save(student);
	    StudentResponse response= new StudentResponse();
	    response.setStudentId(student2.getStudentId());
	    response.setStudentName(student2.getStudentName());
	    response.setStudentGrade(student2.getStudentGrade());
	    structure.setData(response);
	    structure.setMessage("student added sucessfully");
	    structure.setStatus(HttpStatus.CREATED.value());
		
		return new ResponseEntity<ResponseStructure<StudentResponse>>(structure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> updateStudent(Student student, int studentId) {
		Optional<Student> optional = repo.findById(studentId);
		ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
		if(optional.isPresent()) {
			
			Student student2=optional.get();
			
			student2.setStudentId(studentId);
			
			Student student3 = repo.save(student);
			StudentResponse response = new StudentResponse();
			response.setStudentId(student3.getStudentId());
			response.setStudentName(student3.getStudentName());
			response.setStudentGrade(student3.getStudentGrade());
			
			structure.setData(response);
			structure.setMessage("updated successfully");
			structure.setStatus(HttpStatus.CREATED.value());
			
			
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure,HttpStatus.OK);
		}
		else {
		return null;
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> deleteStudent(int studentId) {
		Optional<Student> optional = repo.findById(studentId);
		ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
		if(optional.isPresent()) {
			Student student = optional.get();
			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());
			repo.deleteById(studentId);
			structure.setData(response);
			structure.setMessage("deleted successfully");
			structure.setStatus(HttpStatus.OK.value());
	
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure,HttpStatus.OK);
		}
		else {
		return null;
		}
	}

	@Override
	public ResponseEntity<ResponseStructure<StudentResponse>> findStudentById(int studentId) {
		Optional<Student> optional = repo.findById(studentId);
		ResponseStructure<StudentResponse> structure = new ResponseStructure<StudentResponse>();
		if(optional.isPresent()) {
			Student student = optional.get();
			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());
			
			structure.setData(response);
			structure.setMessage("Student Detalies Found");
			structure.setStatus(HttpStatus.FOUND.value());
			
			return new ResponseEntity<ResponseStructure<StudentResponse>>(structure,HttpStatus.FOUND);
		}
		else {
	
		return null;
		}
	}

	@Override
	public ResponseEntity<List<Student>> findAllStudents() {
		List<Student> list = new ArrayList<Student>();
		
		return null;
	}

	@Override
	public ResponseEntity<StudentResponse> findByEmail(String studentEmail) {
		Student student = repo.findByStudentEmail(studentEmail);
		StudentResponse response = new StudentResponse();
		if(student != null) {
//			StudentResponse response = new StudentResponse();
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());
		}
		else {
			return null;
		}
		return new ResponseEntity<StudentResponse>(response,HttpStatus.FOUND);
	}
	
	
	@Override
	public ResponseEntity<StudentResponse> findByPhNo(long phone) {
		Student student = repo.findByStudentPhNo(phone);
		StudentResponse response = new StudentResponse();
		if(student!=null) {
			response.setStudentId(student.getStudentId());
			response.setStudentName(student.getStudentName());
			response.setStudentGrade(student.getStudentGrade());
		}
		return null;
	}

	@Override
	public ResponseEntity<String> extractDataFromExcel(MultipartFile file)throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
		for(Sheet sheet:workbook) {
			for(Row row: sheet) {
				if(row.getRowNum()>0) {
					
					String name = row.getCell(0).getStringCellValue();
					String email = row.getCell(1).getStringCellValue();
					long phoneNumber = (long) row.getCell(2).getNumericCellValue();
					String grade = row.getCell(3).getStringCellValue();
					String password = row.getCell(4).getStringCellValue();
					
					System.out.println(name+","+email+","+phoneNumber+","+grade+","+","
							+ ""+password);
					Student student = new Student();
					student.setStudentName(name);
					student.setStudentGrade(grade);
					student.setStudentEmail(email);
					student.setStudentPhNo(phoneNumber);
					student.setStudentPassword(password);
					
					repo.save(student);
				}
			}
		}
		workbook.close();
		return null;
	}
	@Override
	public ResponseEntity<String> extractDataFromCsv(MultipartFile file) throws IOException {
		Reader reader = new InputStreamReader(file.getInputStream());
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
		List<CSVRecord> csvRecords = csvParser.getRecords();
		for(CSVRecord csvRecord:csvRecords) {
			if(csvRecord.get(0)!="name") {
			String name = csvRecord.get(0);
			String email=csvRecord.get(1);
			long phoneNumber = Long.parseLong(csvRecord.get(2));
			String grade=csvRecord.get(3);
			String password = csvRecord.get(4);
			System.out.println(name+" @ "+email+" @ "+phoneNumber+" @ "+grade+" @ "+password);
			
			}
		}
				
					
					
				
	
		return new ResponseEntity<String>("data return successfully",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> writeToExcel(String filePath) throws IOException {
		List<Student> users = repo.findAll();
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("studentId");
		header.createCell(1).setCellValue("studentName");
		header.createCell(2).setCellValue("studentEmail");
		header.createCell(3).setCellValue("studentPhNo");
		header.createCell(4).setCellValue("studentGrade");
		header.createCell(5).setCellValue("studentPassword");
		
		int rowNum=1;
		for(Student student : users) {
			Row row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue(student.getStudentId());
			row.createCell(1).setCellValue(student.getStudentName());
			row.createCell(2).setCellValue(student.getStudentEmail());
			row.createCell(3).setCellValue(student.getStudentPhNo());
			row.createCell(4).setCellValue(student.getStudentGrade());
			row.createCell(5).setCellValue(student.getStudentPassword());
		}
		
		FileOutputStream fileOutputStream = new FileOutputStream(filePath);
		workbook.write(fileOutputStream);
		workbook.close();
		
		return new ResponseEntity<String>("data transfered to excel sheet",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sendMail(MessageData messageData) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(messageData.getTo());
		message.setSubject(messageData.getSubject());
		message.setText(messageData.getText());
		message.setSentDate(new Date());
		
		javaMailSender.send(message);
		
		return new ResponseEntity<String>("Mail send successfully", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> sendMailMessage(MessageData data) throws MessagingException {
		
		return null;
	}

	



}


//mdrhdsvsjyieqmsd
