package com.b2w.starwars.api.services.exceptions;

public class ParametroObrigatorioException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ParametroObrigatorioException(String msg) {
		super(msg);
	}
}
