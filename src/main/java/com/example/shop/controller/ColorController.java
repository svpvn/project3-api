package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.ColorDTO;
import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.service.ColorService;
import com.example.shop.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/color")
public class ColorController {
	@Autowired
	ColorService colorService;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid ColorDTO colorDTO){
		colorService.create(colorDTO);
		return ResponseDTO.<Void>builder().status(200).message("ok").build();
	}

	@DeleteMapping("/{id}") //role/12
	public ResponseDTO<Void> delete(@PathVariable("id") int id){
		colorService.delete(id);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
	@PutMapping("/")
	public ResponseDTO<Void> update(@RequestBody @Valid ColorDTO colorDTO){
		colorService.update(colorDTO);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
	
}
