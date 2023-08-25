package com.example.shop.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import lombok.Data;
@Data
public class CouponDTO {
	private int id;
	private String couponCode;
	private double discountAmount;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "Asia/Ho_Chi_Minh") 
	private Date expiredDate;
}
