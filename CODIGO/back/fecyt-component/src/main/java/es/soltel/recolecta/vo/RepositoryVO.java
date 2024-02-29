package es.soltel.recolecta.vo;

import java.util.ArrayList;
import java.util.List;

public class RepositoryVO {

    private Long id;
    private Integer nDeleteState;
    private InstitucionVO institucion;


    // Getters and Setters

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

    public InstitucionVO getInstitucion() {
        return institucion;
    }

    public void setInstitucion(InstitucionVO institucion) {
        this.institucion = institucion;
    }
}

