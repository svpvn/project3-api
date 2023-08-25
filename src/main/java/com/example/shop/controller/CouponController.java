package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.CouponDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.service.CouponService;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	@Autowired
	CouponService couponService;
	
	@PostMapping("/")
	public ResponseDTO<Void> create(@RequestBody CouponDTO couponDTO){
		couponService.create(couponDTO);
		return ResponseDTO.<Void>builder().status(200).message("Ok").build();
	}
}
