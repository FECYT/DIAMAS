package es.soltel.recolecta.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "titles")
public class TitleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(mappedBy = "title")
    private List<InitialQuestionEntity> initialQuestions = new ArrayList<>();

    @Column(name = "eng", length = 2000, nullable = true)
    private String eng;

    @Column(name = "esp", length = 2000, nullable = true)
    private String esp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<InitialQuestionEntity> getInitialQuestions() {
        return initialQuestions;
    }

    public void setInitialQuestions(List<InitialQuestionEntity> initialQuestions) {
        this.initialQuestions = initialQuestions;
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
