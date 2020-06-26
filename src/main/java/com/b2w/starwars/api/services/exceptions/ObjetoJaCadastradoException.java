package com.b2w.starwars.api.services.exceptions;

public class ObjetoJaCadastradoException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ObjetoJaCadastradoException(String msg) {
		super(msg);
	}
}
