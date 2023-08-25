package com.example.shop.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtTokenService {
	@Value("${jwt.secret:123}")
	private String secretKey;
	private long validity = 5; // 5 phut

	// Tạo token
	public String createToken(String username) {
		// Clams chứa các thông tin cần thiết cho token
		Claims claims = Jwts.claims().setSubject(username);
		Date now = new Date();
		Date exp = new Date(now.getTime() + validity * 60 * 1000);

		return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	// kiem tra token con hieu luc ko
	public boolean isValidToken(String token) {
		try {
			// tra ve claims
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}

		return false;
	}

	public String getUsername(String token) {
		try {
			return 	Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
		} catch (Exception e) {
				// TODO: handle exception
		}
		return null;
	}
}
