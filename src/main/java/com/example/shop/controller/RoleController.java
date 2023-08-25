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

import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.service.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/role")
public class RoleController {
	@Autowired
	RoleService roleService;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody @Valid RoleDTO roleDTO){
		roleService.create(roleDTO);
		return ResponseDTO.<Void>builder().status(200).message("ok").build();
	}

	@DeleteMapping("/{id}") //role/12
	public ResponseDTO<Void> delete(@PathVariable("id") int id){
		roleService.delete(id);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
	@PutMapping("/")
	public ResponseDTO<Void> update(@RequestBody @Valid RoleDTO roleDTO){
		roleService.update(roleDTO);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<RoleDTO>> search(@ModelAttribute SearchDTO searchDTO){
		PageDTO<RoleDTO> pageDTO= roleService.search(searchDTO);
		return ResponseDTO.<PageDTO<RoleDTO>>builder().status(200).message("OK").data(pageDTO).build();
	}
}
