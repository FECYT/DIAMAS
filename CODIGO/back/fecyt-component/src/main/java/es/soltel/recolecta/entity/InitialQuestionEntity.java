package es.soltel.recolecta.entity;

import javax.persistence.*;

@Entity
@Table(name = "initial_questions")
public class InitialQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cat_question", nullable = false)
    private CatQuestionEntity catQuestion;

    @OneToOne
    @JoinColumn(name = "title_id", nullable = false)
    private TitleEntity title;

    @Column(name = "orden", nullable = false)
    private Double orden;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "helpText", length = 1000)
    private String helpText;

    @Column(name = "isWritableByEvaluator", nullable = false)
    private Integer isWritableByEvaluator;

    @Column(name = "hasUrlText", nullable = false)
    private Integer hasUrlText;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "hasFileAttach", nullable = false)
    private Integer hasFileAttach;

    @Column(name = "nDeleteState", nullable = false)
    private Integer nDeleteState;

    @Column(name = "negativeMessage", nullable = true)
    private String negativeMessage;

    @Column(name = "hasNegativeExtraResponse",nullable = false)
    private Boolean hasNegativeExtraResponse = false;

    // Getters y setters para las propiedades


    public String getNegativeMessage() {
        return negativeMessage;
    }

    public void setNegativeMessage(String negativeMessage) {
        this.negativeMessage = negativeMessage;
    }

    public Long getId() {
        return id;
    }

    public Boolean getHasNegativeExtraResponse() {
        return hasNegativeExtraResponse;
    }

    public void setHasNegativeExtraResponse(Boolean hasNegativeExtraResponse) {
        this.hasNegativeExtraResponse = hasNegativeExtraResponse;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatQuestionEntity getCatQuestion() {
        return catQuestion;
    }

    public void setCatQuestion(CatQuestionEntity catQuestion) {
        this.catQuestion = catQuestion;
    }

    public TitleEntity getTitle() {
        return title;
    }

    public void setTitle(TitleEntity title) {
        this.title = title;
    }

    public Double getOrder() {
        return orden;
    }

    public void setOrder(Double order) {
        this.orden = order;
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
