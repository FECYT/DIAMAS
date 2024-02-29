package es.soltel.recolecta.vo;

import es.soltel.recolecta.entity.QuestionEntity;
import es.soltel.recolecta.entity.QuestionnaireEntity;

public class QuestionnaireQuestionVO {

    private Long id;
    private QuestionnaireVO questionnaire;
    private QuestionVO question;
    private Integer nDeleteState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuestionnaireVO getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(QuestionnaireVO questionnaire) {
        this.questionnaire = questionnaire;
    }

    public QuestionVO getQuestion() {
        return question;
    }

    public void setQuestion(QuestionVO question) {
        this.question = question;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}