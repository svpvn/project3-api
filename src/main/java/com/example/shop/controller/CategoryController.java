package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.CategoryDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.service.CategoryService;
import com.example.shop.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid CategoryDTO categoryDTO){
		categoryService.create(categoryDTO);
		return ResponseDTO.<Void>builder().status(200).message("ok").build();
	}
	
	@DeleteMapping("/{id}") //role/12
	public ResponseDTO<Void> delete(@PathVariable("id") int id){
		categoryService.delete(id);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
	@PutMapping("/")
	public ResponseDTO<Void> update(@RequestBody @Valid CategoryDTO categoryDTO){
		categoryService.update(categoryDTO);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
}
