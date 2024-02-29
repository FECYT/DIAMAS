package es.soltel.recolecta.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "repository")
public class RepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nDeleteState")
    private Integer nDeleteState;

    @OneToMany(mappedBy = "repository", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRepositoryEntity> userRepositories = new HashSet<>();
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "institucion_id")
    private InstitucionEntity institucion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getnDeleteState() {
        return nDeleteState;
    }

    public void setnDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }

    public Set<UserRepositoryEntity> getUserRepositories() {
        return userRepositories;
    }

    public void setUserRepositories(Set<UserRepositoryEntity> userRepositories) {
        this.userRepositories = userRepositories;
    }
    
    public InstitucionEntity getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitucionEntity institucion) {
        this.institucion = institucion;
    }
}

