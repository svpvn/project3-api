package com.example.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.shop.dto.PageDTO;
import com.example.shop.dto.ResponseDTO;
import com.example.shop.dto.RoleDTO;
import com.example.shop.dto.SearchDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.entity.Role;
import com.example.shop.entity.User;
import com.example.shop.repository.RoleRepo;
import com.example.shop.repository.UserRepo;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

public interface UserService {
	void create(UserDTO userDTO);
	void update(UserDTO userDTO);
	void updatePassword(UserDTO userDTO);
	void delete(int id);
	PageDTO<UserDTO> search(SearchDTO searchDTO);
	UserDTO findByUsername(String username);
	void forgotPassword(String username);
	
}
@Service
class UserServiceImpl implements UserService ,UserDetailsService {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	EmailService emailService;
	
	@Override	
	@Transactional
	public void create(UserDTO userDTO) {

		User user = new ModelMapper().map(userDTO, User.class);

		user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		userRepo.save(user);
		// tra ve idsau khi tao
		userDTO.setId(user.getId());
	}

	@Override
	public void update(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
		user.setName(userDTO.getName());
		user.setBirthdate(userDTO.getBirthdate());
		user.setAvatar(userDTO.getAvatar());
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());
		List<Role> list = new ArrayList<>();
		for(RoleDTO roleDTO : userDTO.getRoles()) {
			list.add(new ModelMapper().map(roleDTO, Role.class));
		}
		user.setRoles(list);
		userRepo.save(user);
	}

	@Override
	public void delete(int id) {
		userRepo.deleteById(id);;	
	}

	@Override
	public PageDTO<UserDTO> search(SearchDTO searchDTO) {
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
		Page<User> page = userRepo.searchByName("%"+ searchDTO.getKeyword() +"%", pageRequest);
		PageDTO<UserDTO> pageDTO = new PageDTO<>();
		
		pageDTO.setTotalElements(page.getTotalElements());
		pageDTO.setTotalPages(page.getTotalPages());
		List<UserDTO> list = new ArrayList<>();
		for(User u : page.getContent()) {
			list.add(convert(u));
		}
		pageDTO.setData(list);
		return pageDTO;
	}

	@Override
	public void updatePassword(UserDTO userDTO) {
		User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException ::new);
		user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
		userRepo.save(user);
	}
	
	private UserDTO convert(User user) {
		return new ModelMapper().map(user, UserDTO.class);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = userRepo.findByUsername(username);
		if(userEntity == null) {
			throw new UsernameNotFoundException("Not Found");
		}
		//convert userEntity -> userDetails
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		// Chuyển vai trò về quyền trong security
		for(Role role : userEntity.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));	
		}
		return new org.springframework.security.core.userdetails.User(username,userEntity.getPassword(),authorities);
	}

	@Override
	public UserDTO findByUsername(String username) {
		UserDTO u = new ModelMapper().map(userRepo.findByUsername(username), UserDTO.class);
		return u;
	}
	private String generateRandomPassword(int length) {
		 String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		 StringBuilder builder = new StringBuilder();
		 Random random = new Random();
		 for(int i=0; i<length ;i++) {
			 int index = random.nextInt(characters.length());
			 builder.append(characters.charAt(index));
		 }
		 return builder.toString();
	}

	@Override
	public void forgotPassword(String username) {
		User user = userRepo.findByUsername(username);
		if(user == null) {
				System.out.println("Khong tim thay user");
		}
		String password = generateRandomPassword(6);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		emailService.sendMail( user.getEmail(), "Mật khẩu thay đổi của bạn" , password);
		userRepo.save(user);
	}
	
}
