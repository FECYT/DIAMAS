package es.soltel.herramientaevaluador.common.config.seguridad;

public class JwtConfig {

	private String uri;

	private String header;

	private String prefix;

	private int expiration;

	private String secret;

	public String getUri() {
		return uri;
	}

	public String getHeader() {
		return header;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public String getSecret() {
		return secret;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}
}
