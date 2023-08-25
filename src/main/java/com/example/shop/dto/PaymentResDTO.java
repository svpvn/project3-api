package com.example.shop.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class PaymentResDTO implements Serializable{
	
	private String status;
	private String message;
	private String URL;
}
