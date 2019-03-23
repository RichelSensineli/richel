package io.richel.curso.services.exceptions;

public class DataIntegrityException extends RuntimeException{
	private static final long serialVersionUID = -3862128984984773420L;

	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
