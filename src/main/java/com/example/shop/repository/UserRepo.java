package com.example.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.shop.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	
	@Query("SELECT u FROM User u WHERE u.name LIKE :name") // câu lệnh của jbql
	Page<User> searchByName (@Param("name") String s,Pageable pageable);
	
	@Query("SELECT u FROM User u WHERE MONTH(u.birthdate) = :month AND DAY(u.birthdate) = :date")
	List<User> searchByBirthday(@Param("date") int date, @Param("month") int month);
	// Select user where username = ?
	User findByUsername(String username);
}
