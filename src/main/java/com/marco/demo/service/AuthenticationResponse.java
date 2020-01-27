package com.marco.demo.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//used lombok library
//

@Data
@AllArgsConstructor
public class AuthenticationResponse {

	private String authenticationToken;
	private String username;

}
