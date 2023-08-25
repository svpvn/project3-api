package com.example.shop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@Value("${upload.folder}")
	private String UPLOAD_FOLDERR;;
	
	@PostMapping("/")
	public ResponseDTO<UserDTO> create(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException, IOException{
		if (userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
			if (!(new File(UPLOAD_FOLDERR).exists())) {
				new File(UPLOAD_FOLDERR).mkdirs();
			}
			// lay ten file anh tu doi tuong UserDTO
			String filename = userDTO.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
//			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;
			// luu file vao o cung may chu	
			File newFile = new File(UPLOAD_FOLDERR + newFilename);
			// di chuyen file anh duoc tai len vao doi tuong newFile
			userDTO.getFile().transferTo(newFile);
			
			userDTO.setAvatar(newFilename);// save to db
		}
		userService.create(userDTO);
		return ResponseDTO.<UserDTO>builder().status(200).message("ok").data(userDTO).build();
	}
	
	@PutMapping("/")
	public ResponseDTO<UserDTO> update(@ModelAttribute @Valid UserDTO userDTO) throws IllegalStateException, IOException{
		if (userDTO.getFile() != null && !userDTO.getFile().isEmpty()) {
			// lay ten file anh tu doi tuong UserDTO
			String filename = userDTO.getFile().getOriginalFilename();
			// lay dinh dang file
			String extension = filename.substring(filename.lastIndexOf("."));
			// tao ten moi
			String newFilename = UUID.randomUUID().toString() + extension;
			// luu file vao o cung may chu	
			File newFile = new File(UPLOAD_FOLDERR + newFilename);
			// di chuyen file anh duoc tai len vao doi tuong newFile
			userDTO.getFile().transferTo(newFile);
			
			userDTO.setAvatar(newFilename);// save to db
		}
		userService.update(userDTO);
		return ResponseDTO.<UserDTO>builder().status(200).message("ok").build();
	}
	
	@GetMapping("/download/{filename}")
	public void download(@PathVariable("filename") String filename, HttpServletResponse response) throws IOException {
		File file = new File(UPLOAD_FOLDERR + filename);
		Files.copy(file.toPath(), response.getOutputStream());
	}
	
	@DeleteMapping("/{id}")
	public ResponseDTO<UserDTO> delete(@PathVariable("id") int id) {
		userService.delete(id);
		return ResponseDTO.<UserDTO>builder().status(200).message("ok").build();
	}
	@PostMapping("/search")
	public ResponseDTO<PageDTO<UserDTO>> search(@ModelAttribute SearchDTO searchDTO){
		PageDTO<UserDTO> pageDTO= userService.search(searchDTO);
		return ResponseDTO.<PageDTO<UserDTO>>builder().status(200).message("OK").data(pageDTO).build();
	}
	@PostMapping("/update-pass")
	public ResponseDTO<Void> updatePassword(@RequestBody @Valid UserDTO userDTO){
		userService.updatePassword(userDTO);
		return ResponseDTO.<Void>builder().status(200).message("OK").build();
	}
	
	@PostMapping("/forgot-pass")
	public ResponseDTO<Void> forgorPassword(@RequestParam("username") String username){
		userService.forgotPassword(username);
		return ResponseDTO.<Void>builder().status(200).message("Ok").build();
	}
}
