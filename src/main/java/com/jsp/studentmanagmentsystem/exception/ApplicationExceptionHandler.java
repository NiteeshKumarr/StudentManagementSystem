package com.jsp.studentmanagmentsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jsp.studentmanagmentsystem.util.ErrorStructure;

public class ApplicationExceptionHandler {
	
	public ResponseEntity<ErrorStructure> studentNotFoundById(StudentNotFoundException ex){
		ErrorStructure structure = new ErrorStructure();
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setMessage("ex.getMessage()");
		structure.setRootCause("Student is not present with the requested Id");
		
		return new ResponseEntity<ErrorStructure>(structure,HttpStatus.NOT_FOUND);
				
	}

}
