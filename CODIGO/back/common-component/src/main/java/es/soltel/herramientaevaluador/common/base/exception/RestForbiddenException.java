package es.soltel.herramientaevaluador.common.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RestForbiddenException extends RestException {

	private static final long serialVersionUID = -6857754890780247914L;

	public RestForbiddenException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestForbiddenException(String message) {
		super(message);
	}

	public RestForbiddenException(Throwable cause) {
		super(cause);
	}

}
