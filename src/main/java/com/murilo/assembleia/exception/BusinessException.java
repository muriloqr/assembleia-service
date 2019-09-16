package com.murilo.assembleia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 941891198189L;

	public BusinessException(String mensagem) {
		super(mensagem);
	}
}
