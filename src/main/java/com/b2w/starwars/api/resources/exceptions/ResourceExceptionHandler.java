package com.b2w.starwars.api.resources.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.b2w.starwars.api.services.exceptions.ObjetoJaCadastradoException;
import com.b2w.starwars.api.services.exceptions.ObjetoNaoEncontradoException;
import com.b2w.starwars.api.services.exceptions.ParametroObrigatorioException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado(ObjetoNaoEncontradoException e, HttpServletRequest request) {
		
		HttpStatus status =  HttpStatus.NOT_FOUND;
		
		StandardError err = new StandardError(System.currentTimeMillis(),
											status.value(),
											"OBJETO NÃO ENCONTRADO!",
											e.getMessage(),
											request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ParametroObrigatorioException.class)
	public ResponseEntity<StandardError> parametroObrigatorio(ParametroObrigatorioException e, HttpServletRequest request) {
		
		HttpStatus status =  HttpStatus.BAD_REQUEST;
		
		StandardError err = new StandardError(System.currentTimeMillis(),
											status.value(),
											"PARÂMETRO OBRIGATÓRIO NÃO INFORMADO!",
											e.getMessage(),
											request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ObjetoJaCadastradoException.class)
	public ResponseEntity<StandardError> objetoJaCadastrado(ObjetoJaCadastradoException e, HttpServletRequest request) {
		
		HttpStatus status =  HttpStatus.CONFLICT;
		
		StandardError err = new StandardError(System.currentTimeMillis(),
											status.value(),
											"OBJETO JÁ CADASTRADO!",
											e.getMessage(),
											request.getRequestURI());
		
		return ResponseEntity.status(status).body(err);
	}
}
