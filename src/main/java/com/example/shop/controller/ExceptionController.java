package com.example.shop.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.shop.dto.ResponseDTO;

import lombok.extern.slf4j.Slf4j;


@RestControllerAdvice
@Slf4j
public class ExceptionController {
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	@ResponseStatus(code = HttpStatus.CONFLICT)
	public ResponseDTO<Void> conflict(Exception ex){
		log.info("ex: ", ex);
		return ResponseDTO.<Void>builder().status(200).message("CONFLICT").build();
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ResponseDTO<Void> badInput(MethodArgumentNotValidException ex){
		List<ObjectError> errors = ex.getBindingResult().getAllErrors(); //lay danh sach cac loi

		String msg = "";
		for (ObjectError e : errors) {
			FieldError fieldError = (FieldError) e;

			msg += fieldError.getField() + ":" + e.getDefaultMessage() + ";";
		}
		return ResponseDTO.<Void>builder().status(200).message(msg).build();
	}
}
