package es.soltel.recolecta.vo;

import java.time.LocalDateTime;
import java.util.List;

public class UserVO {

    private Long id;
    private LocalDateTime lastLogin;
    private String nombre;

    private String apellidos;
    private Integer nDeleteState;
    private String email;

    private String pais;

    private String afiliacion_institucional;
    private String password;
    private Boolean active;

    private List<RolRelationVO> roles;

    private List<String> rol;

    public List<String> getRol() {
        return rol;
    }

    public void setRol(List<String> rol) {
        this.rol = rol;
    }

    public List<RolRelationVO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolRelationVO> roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void removePassword() {
        this.password = null;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

	public String getAfiliacion_institucional() {
		return afiliacion_institucional;
	}

	public void setAfiliacion_institucional(String afiliacion_institucional) {
		this.afiliacion_institucional = afiliacion_institucional;
	}
}
