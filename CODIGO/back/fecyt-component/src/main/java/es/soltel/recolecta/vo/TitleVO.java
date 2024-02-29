package es.soltel.recolecta.vo;

import es.soltel.recolecta.entity.InitialQuestionEntity;

import javax.persistence.*;

public class TitleVO {

    private Long id;

    private String eng;

    private String esp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getEsp() {
        return esp;
    }

    public void setEsp(String esp) {
        this.esp = esp;
    }
}
