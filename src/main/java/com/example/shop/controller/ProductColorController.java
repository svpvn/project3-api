package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.ProductColorDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.service.ProductColorService;



@RestController
@RequestMapping("/product-color")
public class ProductColorController {
	@Autowired
	ProductColorService productColorService;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody ProductColorDTO productColorDTO){
		productColorService.create(productColorDTO);
		return ResponseDTO.<Void>builder().status(200).message("Ok").build();
	}
	@PutMapping("/")
	public ResponseDTO<Void> update(@RequestBody ProductColorDTO productColorDTO){
		productColorService.update(productColorDTO);
		return ResponseDTO.<Void>builder().status(200).message("Ok").build();
	}
	@DeleteMapping("/{id}")
	public ResponseDTO<Void> delete(@PathVariable int id){
		productColorService.delete(id);
		return ResponseDTO.<Void>builder().status(200).message("Ok").build();
	}
}
