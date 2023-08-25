package com.example.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ProductDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.entity.Product;
import com.example.shop.repository.ProductRepo;

import jakarta.persistence.NoResultException;

public interface ProductService {
	void create(ProductDTO productDTO);

	void update(ProductDTO productDTO);

	void delete(int id);
	
	ProductDTO getById(int id);
	
	PageDTO<ProductDTO> searchh(SearchDTO searchDTO);
	
}

@Service
class ProductServiceImpl implements ProductService{
	@Autowired 
	ProductRepo productRepo;


	@Override
	public void create(ProductDTO productDTO) {
//		Category category = categoryRepo.findById(productDTO.getCategory().getId()).orElseThrow(NoResultException ::new);
		Product product = new ModelMapper().map(productDTO, Product.class);
//		product.setCategory(category);	
		productRepo.save(product);
		
	}

	@Override
	public void update(ProductDTO productDTO) {
		// TODO Auto-generated method stub
		Product product = productRepo.findById(productDTO.getId()).orElseThrow(NoResultException ::new);
		product =  new ModelMapper().map(productDTO, Product.class);
		productRepo.save(product);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		productRepo.deleteById(id);
	}

	@Override
	public PageDTO<ProductDTO> searchh(SearchDTO searchDTO) {
		Sort sort = Sort.by("id").ascending();
		if(searchDTO.getSortedField() != null && !searchDTO.getSortedField().isEmpty()) {
			sort =Sort.by(searchDTO.getSortedField());
		}
		if(searchDTO.getCurrentPages() == null) {
			searchDTO.setCurrentPages(0);
		}
		if(searchDTO.getSize() == null) {
			searchDTO.setSize(8);
		}
		if (searchDTO.getKeyword() == null) {
			searchDTO.setKeyword("");
		}
		Pageable pageRequest = PageRequest.of(searchDTO.getCurrentPages(),searchDTO.getSize(),sort);
		Page<Product> page = productRepo.searchByName("%"+ searchDTO.getKeyword() +"%", pageRequest);
		PageDTO<ProductDTO> pageDTO = new PageDTO<>();
		
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		List<ProductDTO> list = new ArrayList<>();
		for(Product p : page.getContent()) {
			list.add(convert(p));
		}
		pageDTO.setData(list);
		return pageDTO;
	}

	@Override
	public ProductDTO getById(int id) {
		Product product = productRepo.findById(id).orElseThrow(NoResultException ::new);
		return new ModelMapper().map(product, ProductDTO.class);
	}
	
	private ProductDTO convert(Product product) {
		return new ModelMapper().map(product, ProductDTO.class);
	}
}