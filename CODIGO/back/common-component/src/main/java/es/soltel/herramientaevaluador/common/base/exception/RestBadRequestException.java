package es.soltel.herramientaevaluador.common.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RestBadRequestException extends RestException {

	private static final long serialVersionUID = -6857754890780247914L;

	public RestBadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestBadRequestException(String message) {
		super(message);
	}

	public RestBadRequestException(Throwable cause) {
		super(cause);
	}

}