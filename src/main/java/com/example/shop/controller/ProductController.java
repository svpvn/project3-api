package com.example.shop.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.BillDTO;
import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ProductDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Value("${upload.folder2}")
	private String UPLOAD_FOLDER;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@ModelAttribute @Valid ProductDTO productDTO) throws IllegalStateException, IOException{
		if (productDTO.getFile() != null && !productDTO.getFile().isEmpty()) {
			if (!(new File(UPLOAD_FOLDER).exists())) {
				new File(UPLOAD_FOLDER).mkdirs();
			}
			// lay ten file anh tu doi tuong UserDTO
			String filename = productDTO.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;
			// luu file vao o cung may chu	
			File newFile = new File(UPLOAD_FOLDER + newFilename);
			// di chuyen file anh duoc tai len vao doi tuong newFile
			productDTO.getFile().transferTo(newFile);
			
			productDTO.setImage(newFilename);// save to db
		}
		productService.create(productDTO);
		return ResponseDTO.<Void>builder().status(200).message("ok").build();
	}
	
	@DeleteMapping("/{id}") //role/12
	public ResponseDTO<Void> delete(@PathVariable("id") int id){
		productService.delete(id);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}

	@PutMapping("/")
	public ResponseDTO<Void> update(@ModelAttribute @Valid ProductDTO productDTO) throws IllegalStateException, IOException{
		if (productDTO.getFile() != null && !productDTO.getFile().isEmpty()) {
			// lay ten file anh tu doi tuong UserDTO
			String filename = productDTO.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;
			// luu file vao o cung may chu	
			File newFile = new File(UPLOAD_FOLDER + newFilename);
			// di chuyen file anh duoc tai len vao doi tuong newFile
			productDTO.getFile().transferTo(newFile);
			
			productDTO.setImage(newFilename);// save to db
		}
		productService.update(productDTO);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
	@PostMapping("/search")
	public ResponseDTO<PageDTO<ProductDTO>> search(@RequestBody SearchDTO searchDTO){
		PageDTO<ProductDTO> pageDTO= productService.searchh(searchDTO);
		return ResponseDTO.<PageDTO<ProductDTO>>builder().status(200).message("OK").data(pageDTO).build();
	}
	
	@GetMapping("/")
	public ResponseDTO<ProductDTO> get(@RequestParam("id") int id) {
		return ResponseDTO.<ProductDTO>builder().status(200).data(productService.getById(id)).build();
	}
}
