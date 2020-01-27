package com.marco.demo.security;

//JWT PROVIDER CLASS
//implemented Keystore

import javax.annotation.PostConstruct;





import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.marco.demo.exception.SpringBlogException;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;

import java.io.*;
import java.security.*;
import java.security.cert.*;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {
	
	private KeyStore keyStore;
	
	@PostConstruct
	public void init()
	{
		try
		{
			keyStore = KeyStore.getInstance("JKS");
			InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
			keyStore.load(resourceAsStream, "8540794".toCharArray());
		}
		catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e)
		{
			throw new SpringBlogException("Exception occured while loading keystore");
		}
	}
	
	public String generateToken(Authentication authentication)
	{
		User principal = (User) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(principal.getUsername())
				.signWith(getPrivateKey())
				.compact();
	}
	
	private PrivateKey getPrivateKey() {
		try
		{
			return (PrivateKey)keyStore.getKey("springblog", "8540794".toCharArray());
		}
		catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e)
		{
			throw new SpringBlogException("Exception occured while retrieving private key from keystore");
		}
	}

	public boolean validateToken(String jwt)
	{
		Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
		return true;
	}

	private PublicKey getPublicKey() {
		
		try
		{
			return keyStore.getCertificate("springblog").getPublicKey();
		}
		catch(KeyStoreException e)
		{
			throw new SpringBlogException("Exception occured while retrieving public key from keystore");
		}
	}

	public String getUsernameFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(getPublicKey())
				.parseClaimsJws(token)
				.getBody();
		
		return claims.getSubject();
		
	}
	
//	public boolean validateToken(String jwt)
//	{
//		Jwts.parser().setSigningKey(key).parseClaimsJws(jwt);
//		return true;
//	}
	

//	public String getUsernameFromJWT(String token) {
//		Claims claims = Jwts.parser()
//				.setSigningKey(key)
//				.parseClaimsJws(token)
//				.getBody();
//		
//		return claims.getSubject();
//		
//	}
	
	
}
