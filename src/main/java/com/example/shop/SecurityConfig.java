package com.example.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration // được sử dụng để đánh dấu lớp là một cấu hình và cho phép bạn tạo và cấu hình các bean trong ứng dụng Spring Boot.
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	
	@Autowired
	JwtTokenFilter tokenFilter;
	
	@Autowired
	UserDetailsService userDetailsService;
	// Xác thực(Đăng nhập username, password)
	
	
	
	@Autowired
	public void config(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
		
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(
				requests -> requests.requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
				.requestMatchers("/customer/**").authenticated()
				.anyRequest().permitAll())
				// yêu cầu người dùng cung cấp tên người dùng và mật khẩu khi truy cập.
				// Customizer.withDefaults() được sử dụng để cấu hình các giá trị mặc định cho xác thực HTTP Basic.
				.httpBasic(Customizer.withDefaults()).csrf().disable()
				.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class).build();
		
		//cau hinh thieu ma
		// Apply JWT
				
	}
}
