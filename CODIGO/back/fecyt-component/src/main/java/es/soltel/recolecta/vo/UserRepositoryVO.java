package es.soltel.recolecta.vo;


public class UserRepositoryVO {

    private Long id;
    private UserVO users; // Modificado a una lista
    private RepositoryVO repositories; // Modificado a una lista
    private Integer nDeleteState;
    
    public void removePassword() {
        this.users.removePassword();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserVO getUser() { // Getter modificado para manejar una lista
        return users;
    }

    public void setUser(UserVO users) { // Setter modificado para manejar una lista
        this.users = users;
    }

    public RepositoryVO getRepository() { // Getter modificado para manejar una lista
        return repositories;
    }

    public void setRepository(RepositoryVO repositories) { // Setter modificado para manejar una lista
        this.repositories = repositories;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
