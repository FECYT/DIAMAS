package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

import es.soltel.recolecta.entity.QuestionEntity;

public class EvaluationResponseVO {
    
    private Long id;
    private String evaluatorCommentary;
    private LocalDateTime lastEdited;
    private Integer nDeleteState;
    private QuestionnaireQuestionVO questionnaireQuestion;
    
    // Getters and setters...

        // Getters

        public Long getId() {
            return id;
        }
    
        public QuestionnaireQuestionVO getQuestionnaireQuestion() {
            return questionnaireQuestion;
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
    
        public void setQuestionnaireQuestion(QuestionnaireQuestionVO questionnaireQuestion) {
            this.questionnaireQuestion = questionnaireQuestion;
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
