package es.soltel.recolecta.entity;

import javax.persistence.*;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "nombre")
    private String nombre;

    @Column(name= "apellidos")
    private String apellidos;

    @Column(name= "afiliacion_institucional")
    private String afiliacion_institucional;

    @Column(name="pais")
    private String pais;

    @Column(name = "n_delete_state")
    private Integer nDeleteState;

    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<EvaluationActionHistoryEntity> history;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private PasswordRecoverEntity passwordRecover;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserRepositoryEntity> userRepositoryEntities = new ArrayList<>();


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<RolRelationEntity> roles;


    public List<RolRelationEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<RolRelationEntity> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
}
