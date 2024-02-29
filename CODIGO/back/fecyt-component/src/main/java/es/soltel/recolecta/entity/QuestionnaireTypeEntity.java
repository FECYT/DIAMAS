package es.soltel.recolecta.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "questionnaire_type")
public class QuestionnaireTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "es")
    private String es;
    
    @Column(name = "en")
    private String en;

    @Column(name = "ndelete_state")
    private Integer nDeleteState;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEs() {
        return es;
    }

    public void setEs(String es) {
        this.es = es;
    }
    
    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }



}
