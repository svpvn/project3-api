package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.Color;

public interface ColorRepo extends JpaRepository<Color, Integer> {

}
