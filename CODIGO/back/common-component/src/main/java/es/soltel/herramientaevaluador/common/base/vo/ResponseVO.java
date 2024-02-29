package es.soltel.herramientaevaluador.common.base.vo;

import java.util.List;

public class ResponseVO<T> {

	private DataVO<T> data;
	private List<MessageResponseVO> messages;

	public DataVO<T> getData() {
		return data;
	}

	public void setData(DataVO<T> data) {
		this.data = data;
	}

	public List<MessageResponseVO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageResponseVO> messages) {
		this.messages = messages;
	}

}
