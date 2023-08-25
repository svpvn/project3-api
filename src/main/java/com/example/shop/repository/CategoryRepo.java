package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
