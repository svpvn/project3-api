package com.example.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.example.shop.dto.CategoryDTO;
import com.example.shop.dto.PageDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.entity.Category;
import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.repository.CategoryRepo;

import jakarta.persistence.NoResultException;

public interface CategoryService {
	void create(CategoryDTO categoryDTO);

	void update(CategoryDTO categoryDTO);

	void delete(int id);
	
	CategoryDTO getById(int id);

	PageDTO<CategoryDTO> search(SearchDTO searchDTO);
}

@Service
class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public void create(CategoryDTO categoryDTO) {
		Category category = new ModelMapper().map(categoryDTO, Category.class);
		categoryRepo.save(category);
		//tra ve id sau khi tao
		categoryDTO.setId(category.getId());
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		Category category = categoryRepo.findById(categoryDTO.getId()).orElseThrow(NoResultException::new);
		category.setName(categoryDTO.getName());
		categoryRepo.save(category);

	}

	@Override
	public void delete(int id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public PageDTO<CategoryDTO> search(SearchDTO searchDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryDTO getById(int id) {
		Category category = categoryRepo.findById(id).orElseThrow(NoResultException::new);
		return new ModelMapper().map(category, CategoryDTO.class);
	}

}