package com.eucread.eucread.util.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eucread.eucread.users.auth.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	private final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 60 * 1000;

	@Value("${jwt.secret}")
	private String JWT_SECRET;

	private SecretKey getKey() {
		return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(CustomUserDetails userDetails) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME);
		List<String> roles = userDetails.getAuthorities().stream()
			.map(auth -> auth.getAuthority())
			.toList();

		return Jwts.builder()
			.setSubject(userDetails.getUsername())
			.setIssuedAt(now)
			.setExpiration(expiryDate)
			.claim("roles", roles)
			.signWith(getKey())
			.compact();
	}

	public boolean validateToken(String token) {
		try {
			Key key = getKey();
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	public Claims getClaims(String token) {
		Key key = getKey();
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}
}
