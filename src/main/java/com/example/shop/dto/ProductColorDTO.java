package com.example.shop.dto;

import java.util.List;

import com.example.shop.entity.Color;
import com.example.shop.entity.Image;
import com.example.shop.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class ProductColorDTO {

	private int id;
	
	private ProductDTO product;
	
	private ColorDTO color;
	
	private int quantity;
	@JsonIgnore
	private List<Image> images;
}
