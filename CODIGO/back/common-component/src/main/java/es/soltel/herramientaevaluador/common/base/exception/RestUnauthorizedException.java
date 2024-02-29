package es.soltel.herramientaevaluador.common.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RestUnauthorizedException extends RestException {

	private static final long serialVersionUID = -5855308965956556296L;

	public RestUnauthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestUnauthorizedException(String message) {
		super(message);
	}

	public RestUnauthorizedException(Throwable cause) {
		super(cause);
	}

}
