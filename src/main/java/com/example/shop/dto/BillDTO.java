package com.example.shop.dto;

import java.util.List;

import com.example.shop.entity.BillItem;
import com.example.shop.entity.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class BillDTO {
	private Integer id;

	private String status;// new , pending ,active

	private UserDTO user;
	
//	@JsonManagedReference // //Có JsonManagedReference  phải có jsonback, bên class nào chính thì cho JsonManagedReference 
	private List<BillItemDTO> billItems;
	
	private String couponCode;
	private double discount;
}
