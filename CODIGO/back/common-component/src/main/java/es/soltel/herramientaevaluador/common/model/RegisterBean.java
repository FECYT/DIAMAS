package es.soltel.herramientaevaluador.common.model;

public class RegisterBean {

	private String email;
	private String password;

	private String nombre;

	private String apellidos;

	private String ipsp;

	private String acronimo;

	private String url;

	private String afiliacion_institucional;

	private String pais;

	public String getAcronimo() {
		return acronimo;
	}

	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAfiliacion_institucional() {
		return afiliacion_institucional;
	}

	public void setAfiliacion_institucional(String afiliacion_institucional) {
		this.afiliacion_institucional = afiliacion_institucional;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getIpsp() {
		return ipsp;
	}

	public void setIpsp(String ipsp) {
		this.ipsp = ipsp;
	}
}
