package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

public class EvaluationPeriodVO {

    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private String status;
    private String description;
    private Integer deleteState;
    private QuestionnaireTypeVO questionnaireType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(Integer deleteState) {
        this.deleteState = deleteState;
    }

	public QuestionnaireTypeVO getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(QuestionnaireTypeVO questionnaireType) {
		this.questionnaireType = questionnaireType;
	}
}
