package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuestionnaireAnswerVO {

    private Long id;
    private QuestionnaireQuestionVO questionnaireQuestion;
    private String observaciones;
    private String answerText;
    private Boolean answer;
    private FileVO file;
    private LocalDateTime answerDateTime;
    private Integer negativeExtraPoint;
    @JsonProperty("nDeleteState")
    private Integer nDeleteState;

    // Getters

    public Long getId() {
        return id;
    }

    public QuestionnaireQuestionVO getQuestionnaireQuestion() {
        return questionnaireQuestion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public String getAnswerText() {
        return answerText;
    }

    public Integer getNegativeExtraPoint() {
        return negativeExtraPoint;
    }

    public void setNegativeExtraPoint(Integer negativeExtraPoint) {
        this.negativeExtraPoint = negativeExtraPoint;
    }

    public FileVO getFile() {
        return file;
    }

    public LocalDateTime getAnswerDateTime() {
        return answerDateTime;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public Boolean getAnswer() {
        return answer;
    }

    // Setters

    public void setAnswer(Boolean answer){
        this.answer = answer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionnaireQuestion(QuestionnaireQuestionVO questionnaireQuestion) {
        this.questionnaireQuestion = questionnaireQuestion;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setFile(FileVO file) {
        this.file = file;
    }

    public void setAnswerDateTime(LocalDateTime answerDateTime) {
        this.answerDateTime = answerDateTime;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
