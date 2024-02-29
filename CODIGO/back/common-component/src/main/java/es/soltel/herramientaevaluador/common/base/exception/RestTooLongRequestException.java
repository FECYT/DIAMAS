package es.soltel.herramientaevaluador.common.base.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
public class RestTooLongRequestException extends RestException {

    private static final long serialVersionUID = -6857754890780247914L;

    public RestTooLongRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestTooLongRequestException(String message) {
        super(message);
    }

    public RestTooLongRequestException(Throwable cause) {
        super(cause);
    }

}