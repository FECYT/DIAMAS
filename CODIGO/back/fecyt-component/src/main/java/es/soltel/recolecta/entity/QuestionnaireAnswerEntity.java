package es.soltel.recolecta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import java.time.LocalDateTime;

@Entity
@Table(name = "QuestionnaireAnswerEntity", uniqueConstraints = @UniqueConstraint(columnNames = "questionnaire_question_id"))
public class QuestionnaireAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "questionnaire_question_id")
    @OneToOne
    private QuestionnaireQuestionEntity questionnaireQuestion;

    private String observaciones;
    private String answerText;
    
    @Column(nullable = true)
    private Boolean answer;

    @OneToOne
    private FileEntity file;
    private LocalDateTime answerDateTime;
    private Integer nDeleteState;

    private Integer negativeExtraPoint;

    // Getters

    public Long getId() {
        return id;
    }

    public Integer getNegativeExtraPoint() {
        return negativeExtraPoint;
    }

    public void setNegativeExtraPoint(Integer negativeExtraPoint) {
        this.negativeExtraPoint = negativeExtraPoint;
    }

    public QuestionnaireQuestionEntity getQuestionnaireQuestion() {
        return questionnaireQuestion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Boolean getAnswer(){
        return answer;
    }

    public String getAnswerText() {
        return answerText;
    }

    public FileEntity getFile() {
        return file;
    }

    public LocalDateTime getAnswerDateTime() {
        return answerDateTime;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionnaireQuestion(QuestionnaireQuestionEntity questionnaireQuestion) {
        this.questionnaireQuestion = questionnaireQuestion;
    }


    public void setAnswer(Boolean answer){
        this.answer = answer;
    }

    public void setObservaciones(String selectedAnswer) {
        this.observaciones = selectedAnswer;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public void setAnswerDateTime(LocalDateTime answerDateTime) {
        this.answerDateTime = answerDateTime;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
