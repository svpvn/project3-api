package com.example.shop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shop.dto.ColorDTO;
import com.example.shop.entity.Color;
import com.example.shop.repository.ColorRepo;

import jakarta.persistence.NoResultException;

public interface ColorService {
	void create(ColorDTO colorDTO);
	void update(ColorDTO colorDTO);
	void delete(int id);
}
@Service
class ColorServiceImpl implements ColorService{
	
	@Autowired
	ColorRepo colorRepo;
	
	@Override
	public void create(ColorDTO colorDTO) {
		Color color = new ModelMapper().map(colorDTO, Color.class);
		colorRepo.save(color);
	}

	@Override
	public void update(ColorDTO colorDTO) {
		Color currentColor = colorRepo.findById(colorDTO.getId()).orElseThrow(NoResultException :: new);
		currentColor.setName(colorDTO.getName());
		colorRepo.save(currentColor);
		
	}

	@Override
	@Transactional
	public void delete(int id) {
		colorRepo.deleteById(id);
	}
	
}
