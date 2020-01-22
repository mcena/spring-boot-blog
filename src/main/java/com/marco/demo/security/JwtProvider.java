package com.marco.demo.security;

import javax.annotation.PostConstruct;



import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;



import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;




import java.security.*;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {
	
	private Key key;
	
	@PostConstruct
	public void init()
	{
		key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}
	
	public String generateToken(Authentication authentication)
	{
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(key)
				.compact();
	}
	
	public boolean validateToken(String jwt)
	{
		Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
		return true;
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
		
	}
	
	
}
