package es.soltel.recolecta.vo;

public class RolRelationVO {

    private Long id;
    private RolVO rol;
    private UserVO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RolVO getRol() {
        return rol;
    }

    public void setRol(RolVO rol) {
        this.rol = rol;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
