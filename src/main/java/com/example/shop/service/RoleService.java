package com.example.shop.service;

import java.util.List;

import com.example.shop.dto.PageDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;

public interface RoleService {
	void create(RoleDTO roleDTO);
	void update(RoleDTO roleDTO);
	void delete(int id);
	RoleDTO getById(int id);
	List<RoleDTO> getAll();
	PageDTO<RoleDTO> search(SearchDTO searchDTO);
}

