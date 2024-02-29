package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

public class QuestionnaireVO {

    private Long id;
    private EvaluationVO evaluation;
    private String state;
    private LocalDateTime creationDate;
    private EvaluationPeriodVO period;
    private Integer nDeleteState;


    // Getters and Setters...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvaluationVO getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationVO evaluation) {
        this.evaluation = evaluation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public EvaluationPeriodVO getPeriod() {
        return period;
    }

    public void setPeriod(EvaluationPeriodVO period) {
        this.period = period;
    }

    public Integer getnDeleteState() {
        return nDeleteState;
    }

    public void setnDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
