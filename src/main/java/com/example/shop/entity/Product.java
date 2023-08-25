package com.example.shop.entity;

import java.util.List;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Product extends TimeAuditable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; 
	
	private String name;
	private String description;
	private String image; //URl
	private double price;
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL) // xoa 1 thang product thi xoa het productColors
	List<ProductColor> productColors;
	//product 1 - n product color n- 1 color
	@ManyToOne
	private Category category;
}
