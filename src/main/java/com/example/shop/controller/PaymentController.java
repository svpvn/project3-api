package com.example.shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.ResponseDTO;
import com.example.shop.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	 @Autowired
	 private VNPayService vnPayService;

	    @GetMapping("/vnpay-payment")
	    public  ResponseDTO<String> createPayment( HttpServletRequest request){
	        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	        String vnpayUrl = vnPayService.createOrder(5000, "oderInfo", baseUrl);
	        return ResponseDTO.<String>builder().status(200).message("OK").data(vnpayUrl).build();
	    }
	    
	 

}
