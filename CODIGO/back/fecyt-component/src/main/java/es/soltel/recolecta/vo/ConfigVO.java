package es.soltel.recolecta.vo;

public class ConfigVO {

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
