package io.richel.curso.services.exceptions;

public class AuthorizationException extends RuntimeException{
	private static final long serialVersionUID = -3862128984984773420L;

	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
