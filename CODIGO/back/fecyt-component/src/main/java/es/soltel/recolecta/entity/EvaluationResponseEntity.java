package es.soltel.recolecta.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class EvaluationResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionnaireQuestionEntity questionnaireQuestionEntity;
    
    private String evaluatorCommentary;
    private LocalDateTime lastEdited;
    private Integer nDeleteState;

    // Getters

    public Long getId() {
        return id;
    }

    public QuestionnaireQuestionEntity getQuestionnaireQuestion() {
        return questionnaireQuestionEntity;
    }

    public String getEvaluatorCommentary() {
        return evaluatorCommentary;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionnaireQuestion(QuestionnaireQuestionEntity questionnaireQuestionEntity) {
        this.questionnaireQuestionEntity = questionnaireQuestionEntity;
    }

    public void setEvaluatorCommentary(String evaluatorCommentary) {
        this.evaluatorCommentary = evaluatorCommentary;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
