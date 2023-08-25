package com.example.shop.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.Data;
@Data
public class BillItemDTO {
	private int id;
	
	//C2
	//@JsonIgnoreProperties("billItems") // bo qua thuoc tinh billItems trong billDTO
	//C1
//	@JsonBackReference
	@JsonIgnoreProperties("billItems")
	private BillDTO bill;
	
	private ProductDTO product;
	@Min(0)
	private int quantity;
	@Min(0)
	private double price;
}
