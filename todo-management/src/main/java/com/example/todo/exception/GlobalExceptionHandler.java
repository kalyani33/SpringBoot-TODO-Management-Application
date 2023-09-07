package com.example.todo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(TodoAPIException.class)
	public ResponseEntity<ErrorDetails> handleTodoAPIException(TodoAPIException exception,WebRequest webRequest){
		ErrorDetails errordetails = new ErrorDetails(
				LocalDateTime.now(),
				exception.getMessage(),
				webRequest.getDescription(false)
				);
		return new ResponseEntity<ErrorDetails>(errordetails,HttpStatus.BAD_REQUEST);
				
	}

}
