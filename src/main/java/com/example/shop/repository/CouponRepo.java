package com.example.shop.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.entity.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Integer> {
	
	@Query("SELECT c FROM Coupon c WHERE c.couponCode = :x AND c.expiredDate >= :now")
	Coupon findByCoupon(@Param("x") String couponCode , @Param("now") Date now);
}
