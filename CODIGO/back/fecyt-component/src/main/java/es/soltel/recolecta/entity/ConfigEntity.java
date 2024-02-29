package es.soltel.recolecta.entity;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class ConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean automaticRegister;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAutomaticRegister() {
        return automaticRegister;
    }

    public void setAutomaticRegister(Boolean automaticRegister) {
        this.automaticRegister = automaticRegister;
    }

}
