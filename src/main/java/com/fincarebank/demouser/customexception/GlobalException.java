package com.fincarebank.demouser.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fincarebank.demouser.dto.ResponseDTO;

@ControllerAdvice
public class GlobalException 
{
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ResponseDTO> globalException(Exception exception)
//	{
//		ResponseDTO dto = new ResponseDTO("Error while processing the request",HttpStatus.BAD_REQUEST.value());
//		return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
//	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ResponseDTO> customException(Exception exception)
	{
		return new ResponseEntity<>(new ResponseDTO(exception.getMessage()
		,HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
	}
}
