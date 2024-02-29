package es.soltel.herramientaevaluador.common.base.vo;

public class MessageResponseVO {

	private String message;
	private String code;

	public MessageResponseVO(String info, String warning, String error) {

	}
	

	public MessageResponseVO(String message, String code) {
		super();
		this.message = message;
		this.code = code;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
