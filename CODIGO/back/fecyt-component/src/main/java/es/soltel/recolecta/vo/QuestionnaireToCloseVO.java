package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

public class QuestionnaireToCloseVO {

    private Long id;
    private LocalDateTime fecha;
    private QuestionnaireVO questionnaire;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public QuestionnaireVO getQuestionnaire() {
        return questionnaire;
    }
    public void setQuestionnaireId(QuestionnaireVO questionnaire) {
        this.questionnaire = questionnaire;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

}
