package com.example.shop.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ProductColorDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.entity.ProductColor;
import com.example.shop.entity.User;
import com.example.shop.repository.ProductColorRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface ProductColorService {
	void create(ProductColorDTO productColorDTO);

    void update(ProductColorDTO productColorDTO);

    void delete(int id);

}
@Service
class ProductColorServiceImpl implements ProductColorService{
	@Autowired
	ProductColorRepo productColorRepo;
	
	
	@Override
	public void create(ProductColorDTO productColorDTO) {
		ProductColor productColor = new ModelMapper().map(productColorDTO, ProductColor.class);
		productColorRepo.save(productColor);
		
	}

	@Override
	public void update(ProductColorDTO productColorDTO) {
		ProductColor productColor = productColorRepo.findById(productColorDTO.getId()).orElseThrow(NoResultException::new);
		productColor =  new ModelMapper().map(productColorDTO, ProductColor.class);
		productColorRepo.save(productColor);
		
	}

	@Override
	public void delete(int id) {
		productColorRepo.deleteById(id);
		
	}

	

	
	
}
