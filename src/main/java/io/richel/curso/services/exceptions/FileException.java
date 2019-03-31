package io.richel.curso.services.exceptions;

public class FileException extends RuntimeException{
	private static final long serialVersionUID = -8181592532811470070L;

	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
