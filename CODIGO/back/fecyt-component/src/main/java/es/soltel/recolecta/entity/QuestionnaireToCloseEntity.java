package es.soltel.recolecta.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "questionnaire_to_close_entity", uniqueConstraints = @UniqueConstraint(columnNames = "questionnaire_id"))
public class QuestionnaireToCloseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @OneToOne
    @JoinColumn(name = "questionnaire_id", referencedColumnName = "id")
    private QuestionnaireEntity questionnaire;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public QuestionnaireEntity getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(QuestionnaireEntity questionnaire) {
        this.questionnaire = questionnaire;
    }

}