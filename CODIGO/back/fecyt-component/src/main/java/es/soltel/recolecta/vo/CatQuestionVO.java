package es.soltel.recolecta.vo;

public class CatQuestionVO {
    private Long id;
    private String categoryType;
    private String tooltipType;
    private String categoryLongType;
    private Integer nDeleteState;
    private Double orden;
    private Boolean hasQuestion;
    private QuestionnaireTypeVO questionnaireType;

    //Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public String getCategoryLongType() {
        return categoryLongType;
    }

    public void setCategoryLongType(String categoryLongType) {
        this.categoryLongType = categoryLongType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getTooltipType() {
        return tooltipType;
    }

    public void setTooltipType(String tooltipType) {
        this.tooltipType = tooltipType;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }

    public Double getOrden() {
        return orden;
    }

    public void setOrden(Double orden) {
        this.orden = orden;
    }

    public Boolean getHasQuestion() {
        return hasQuestion;
    }

    public void setHasQuestion(Boolean hasQuestion) {
        this.hasQuestion = hasQuestion;
    }

	public QuestionnaireTypeVO getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(QuestionnaireTypeVO questionnaireType) {
		this.questionnaireType = questionnaireType;
	}


}

