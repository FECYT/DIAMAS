package es.soltel.herramientaevaluador.common.base.exception;

public class IAException extends Exception {

	private static final long serialVersionUID = -8263054821191770123L;

	public IAException(String message, Throwable cause) {
		super(message, cause);
	}

	public IAException(String message) {
		super(message);
	}

	public IAException(Throwable cause) {
		super(cause);
	}

}
