package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.ProductColor;

public interface ProductColorRepo extends JpaRepository<ProductColor, Integer> {

}
