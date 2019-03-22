package io.richel.curso.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -3862128984984773420L;

	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
