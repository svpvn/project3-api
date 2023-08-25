package com.example.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shop.entity.BillItem;

public interface BillItemRepo extends JpaRepository<BillItem, Integer>{

}
