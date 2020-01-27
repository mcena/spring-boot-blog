package com.marco.demo.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.marco.demo.model.User;
import com.marco.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.*;

//handles the users that are already registered in the database

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username).orElseThrow(()-> 
		new UsernameNotFoundException("no username found" + username));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), 
				user.getPassword(), getAuthorities("ROLE_USER"));
	}
	
	
	private Collection<? extends GrantedAuthority> getAuthorities(String role_user)
	{
		return Collections.singletonList(new SimpleGrantedAuthority(role_user));
	}

}
