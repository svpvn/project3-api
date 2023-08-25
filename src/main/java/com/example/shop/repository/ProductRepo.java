package com.example.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.entity.Product;
import com.example.shop.entity.User;

public interface ProductRepo extends JpaRepository<Product, Integer>{
	@Query("SELECT p FROM Product p WHERE p.name LIKE :x") // câu lệnh của jbql
	Page<Product> searchByName (@Param("x") String name,Pageable pageable);
	
	Product findByName(String name);
}
