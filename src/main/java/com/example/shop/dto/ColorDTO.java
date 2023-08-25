package com.example.shop.dto;

import java.util.List;

import com.example.shop.entity.ProductColor;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ColorDTO {
	private Integer id;
	@NotBlank
	private String name;
	private List<ProductColorDTO> productColors;
}
