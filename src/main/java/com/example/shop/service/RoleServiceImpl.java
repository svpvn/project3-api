package com.example.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.shop.dto.PageDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.entity.Role;
import com.example.shop.repository.RoleRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleRepo roleRepo;
	
	@Override
	@Transactional
	public void create(RoleDTO roleDTO) {
		Role role = new ModelMapper().map(roleDTO, Role.class);
//		role.setName(roleDTO.getName());
//		role.setId(roleDTO.getId());
		roleRepo.save(role);
		// tra ve idsau khi tao
//		roleDTO.setId(role.getId());
	}

	@Override
	@Transactional
	public void update(RoleDTO roleDTO) {
		Role role = roleRepo.findById(roleDTO.getId()).orElseThrow(NoResultException ::new);
		role =  new ModelMapper().map(roleDTO, Role.class);
		roleRepo.save(role);
	}

	@Override
	@Transactional
	public void delete(int id) {
		roleRepo.deleteById(id);
	}

	@Override
	public PageDTO<RoleDTO> search(SearchDTO searchDTO) {
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
		PageRequest pageRequest = PageRequest.of(searchDTO.getCurrentPages(),searchDTO.getSize(),sort);
		Page<Role> page = roleRepo.searchByName("%"+ searchDTO.getKeyword() +"%", pageRequest);
		PageDTO<RoleDTO> pageDTO = new PageDTO<>();
		
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		List<RoleDTO> list = new ArrayList<>();
		for(Role r : page.getContent()) {
			list.add(convert(r));
		}
		pageDTO.setData(list);
		return pageDTO;
	}
	
	private RoleDTO convert(Role role) {
		return new ModelMapper().map(role, RoleDTO.class);
	}

	@Override
	public RoleDTO getById(int id) {
		Role role = roleRepo.findById(id).orElseThrow(NoResultException::new);
		return convert(role);
	}

	@Override
	public List<RoleDTO> getAll() {
		List<Role> courseList = roleRepo.findAll();
		// java 8
		// chuyen tung phan tu userList sang userDTO xog collect ve list
		return courseList.stream().map(u -> convert(u)).collect(Collectors.toList());
	}

}
