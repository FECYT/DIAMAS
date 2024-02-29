package es.soltel.herramientaevaluador.common.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RestException extends RuntimeException {

	private static final long serialVersionUID = -8263054821191770123L;

	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(Throwable cause) {
		super(cause);
	}

}
