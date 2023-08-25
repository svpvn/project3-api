package com.example.shop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.example.shop.dto.ColorDTO;
import com.example.shop.dto.CouponDTO;
import com.example.shop.entity.Coupon;
import com.example.shop.repository.CouponRepo;

public interface CouponService {
	void create(CouponDTO couponDTO);
}
@Service
class CouponServiceImpl implements CouponService{
	@Autowired
	CouponRepo couponRepo;
	
	@Override
	public void create(CouponDTO couponDTO) {
		Coupon coupon = new ModelMapper().map(couponDTO, Coupon.class);
		couponRepo.save(coupon);
	}


	
}
