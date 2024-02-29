package es.soltel.recolecta.vo;

public class InitialQuestionVO {

    private Long id;
    private CatQuestionVO catQuestionId;
    private String title;
    private Double order;
    private Double weight;
    private String helpText;
    private Integer isWritableByEvaluator;
    private Integer hasUrlText;
    private String type;
    private Integer hasFileAttach;
    private Integer nDeleteState;

    

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatQuestionVO getCatQuestionId() {
        return catQuestionId;
    }

    public void setCatQuestionId(CatQuestionVO catQuestionId) {
        this.catQuestionId = catQuestionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getOrder() {
        return order;
    }

    public void setOrder(Double order) {
        this.order = order;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public Integer getIsWritableByEvaluator() {
        return isWritableByEvaluator;
    }

    public void setIsWritableByEvaluator(Integer isWritableByEvaluator) {
        this.isWritableByEvaluator = isWritableByEvaluator;
    }

    public Integer getHasUrlText() {
        return hasUrlText;
    }

    public void setHasUrlText(Integer hasUrlText) {
        this.hasUrlText = hasUrlText;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getHasFileAttach() {
        return hasFileAttach;
    }

    public void setHasFileAttach(Integer hasFileAttach) {
        this.hasFileAttach = hasFileAttach;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}