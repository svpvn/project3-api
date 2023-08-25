package com.example.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.entity.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{
	@Query("SELECT u FROM Role u WHERE u.name LIKE :x")
	Page<Role> searchByName(@Param("x") String name,Pageable pageable) ;
	
	Role findByName(String name);
}
