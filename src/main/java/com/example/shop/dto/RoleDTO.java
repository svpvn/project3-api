package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDTO {
	private Integer id;
	@NotBlank
	@Size(min = 2, max = 20)
	private String name;
}
