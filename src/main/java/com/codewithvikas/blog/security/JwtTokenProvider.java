package com.codewithvikas.blog.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.codewithvikas.blog.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app-jwt-expiration-milliseconds}")
	private long jwtExpirationDate;
	
	// generating the JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		
		Date currenDate = new Date();
		
		Date expireDate = new Date(currenDate.getTime() + jwtExpirationDate);
		
		String token = Jwts.builder()
						.setSubject(username)
						.setIssuedAt(new Date())
						.setExpiration(expireDate)
						.signWith(key())
						.compact();
		return token;
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(
				Decoders.BASE64.decode(jwtSecret)
				);
	}
	
	// get username from JWT token
	public String getUsername(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token)
			.getBody();
	
		String username = claims.getSubject();
		return username;
	}
	
	// Validate JWT token
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(key())
				.build()
				.parse(token);
			return true;
		}catch (MalformedJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
		}catch (ExpiredJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
		}catch (UnsupportedJwtException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
		}catch (IllegalArgumentException ex) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
		}
	}
}
