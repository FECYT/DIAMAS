package es.soltel.recolecta.vo;

import java.util.ArrayList;
import java.util.List;

public class InstitucionVO {

    private Long id;
    private String nombre;
    private Integer nDeleteState;
    private String acronimo;
    private String url;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getnDeleteState() {
        return nDeleteState;
    }

    public void setnDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }

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
}

