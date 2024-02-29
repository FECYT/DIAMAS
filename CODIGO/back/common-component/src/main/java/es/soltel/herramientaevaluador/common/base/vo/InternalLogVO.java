package es.soltel.herramientaevaluador.common.base.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InternalLogVO implements Serializable {

	private static final long serialVersionUID = -1347023621605823542L;

	private LocalDateTime datetime;

	private String member;

	private String user;

	private String agent;

	private String method;

	private String description;

	public InternalLogVO() {
	}

	public InternalLogVO(String method) {
		this.method = method;
		this.datetime = LocalDateTime.now();
	}

	public InternalLogVO(String method, String description) {
		this.method = method;
		this.description = description;
		this.datetime = LocalDateTime.now();
	}

	public LocalDateTime getDatetime() {
		return this.datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAgent() {
		return this.agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
