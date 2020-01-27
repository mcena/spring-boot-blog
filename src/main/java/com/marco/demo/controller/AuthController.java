package com.marco.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.marco.demo.dto.LoginRequest;
import com.marco.demo.dto.RegisterRequest;
import com.marco.demo.service.AuthService;
import com.marco.demo.service.AuthenticationResponse;

//CONTROLLER FOR api/auth mapping (signup and login)

@RestController
@RequestMapping("api/auth")
public class AuthController {
	@Autowired
    private AuthService authService;
	
	
	@GetMapping("/home")
	public String home()
	{
		return "beep";
	}
 
    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity(HttpStatus.OK);
    }
    
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest)
    {
    	return authService.login(loginRequest);
    } 
    
    
    
}
