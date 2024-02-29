package es.soltel.recolecta.vo;

public class QuestionVO {

    private Long id;

    private CatQuestionVO catQuestion; 
    private TitleVO title;

    private Double orden;

    private Float weight;

    private String helpText;

    private Integer isWritableByEvaluator;

    private Integer hasUrlText;

    private Integer hasFileAttach;

    private Integer nDeleteState;

    private String type;

    private EvaluationPeriodVO periodId;

    private Integer negativeExtraPoint;

    private String negativeMessage;

    private Boolean hasNegativeExtraResponse;

    public QuestionVO() {
    }

    // Getters


    public Integer getNegativeExtraPoint() {
        return negativeExtraPoint;
    }

    public Boolean getHasNegativeExtraResponse() {
        return hasNegativeExtraResponse;
    }

    public void setHasNegativeExtraResponse(Boolean hasNegativeExtraResponse) {
        this.hasNegativeExtraResponse = hasNegativeExtraResponse;
    }

    public void setNegativeExtraPoint(Integer negativeExtraPoint) {
        this.negativeExtraPoint = negativeExtraPoint;
    }

    public String getNegativeMessage() {
        return negativeMessage;
    }

    public void setNegativeMessage(String negativeMessage) {
        this.negativeMessage = negativeMessage;
    }

    public Long getId() {
        return id;
    }

    public CatQuestionVO getCatQuestion() { 
        return catQuestion;
    }



    public Double getOrden() {
        return orden;
    }

    public Float getWeight() {
        return weight;
    }

    public String getHelpText() {
        return helpText;
    }

    public Integer getIsWritableByEvaluator() {
        return isWritableByEvaluator;
    }

    public Integer getHasUrlText() {
        return hasUrlText;
    }

    public Integer getHasFileAttach() {
        return hasFileAttach;
    }

    public Integer getnDeleteState() {
        return nDeleteState;
    }

    public EvaluationPeriodVO getPeriodId() {
        return periodId;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setCatQuestion(CatQuestionVO catQuestion) {
        this.catQuestion = catQuestion;
    }


    public TitleVO getTitle() {
        return title;
    }

    public void setTitle(TitleVO title) {
        this.title = title;
    }

    public void setOrder(Double orden) {
        this.orden = orden;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public void setIsWritableByEvaluator(Integer isWritableByEvaluator) {
        this.isWritableByEvaluator = isWritableByEvaluator;
    }

    public void setHasUrlText(Integer hasUrlText) {
        this.hasUrlText = hasUrlText;
    }

    public void setHasFileAttach(Integer hasFileAttach) {
        this.hasFileAttach = hasFileAttach;
    }

    public void setnDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOrden(Double orden) {
        this.orden = orden;
    }

    public void setPeriodId(EvaluationPeriodVO periodId) {
        this.periodId = periodId;
    }


}
