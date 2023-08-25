package com.example.shop.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
	private int id;
	@NotBlank
	private String name;
	private String avatar; //url
	@NotBlank
	private String username;
	@NotBlank
	@Size(min = 6,max = 20)
	private String password;
	private String email;
	
	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Asia/Ho_Chi_Minh")// // Trường hợp đẩy lên hoặc trả về json
	@DateTimeFormat(pattern = "dd/MM/yyyy") // Sử dụng khi form data
	private Date birthdate;
	
	@JsonIgnore // Bỏ qua file
	private MultipartFile file;
//	@JsonIgnore
	private List<RoleDTO> roles;//roles[0].id=1
	
}
