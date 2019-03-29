package io.richel.curso.services;

import org.springframework.security.core.context.SecurityContextHolder;

import io.richel.curso.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}
}
