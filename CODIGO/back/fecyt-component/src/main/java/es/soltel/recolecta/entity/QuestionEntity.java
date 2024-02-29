package es.soltel.recolecta.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cat_question_id")
    private CatQuestionEntity catQuestion;

    @ManyToOne
    @JoinColumn(name = "period_id")
    private EvaluationPeriodEntity periodId;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private TitleEntity title;

    private Double orden;

    private Float weight;

    @Column(name = "helpText", columnDefinition = "TEXT")
    private String helpText;

    private Integer isWritableByEvaluator;

    private Integer hasUrlText;

    private Integer hasFileAttach;

    private Integer nDeleteState;

    private String negativeMessage;

    @Column(nullable = false)
    private Boolean hasNegativeExtraResponse = false;

    @Enumerated(EnumType.STRING)
    private QuestionType typeQuestion;

    public QuestionEntity() {
    }

    // getters y setters
    // Getters
    public Long getId() {
        return id;
    }

    public CatQuestionEntity getCatQuestion() {
        return catQuestion;
    }

    public Boolean getHasNegativeExtraResponse() {
        return hasNegativeExtraResponse;
    }

    public void setHasNegativeExtraResponse(Boolean hasNegativeExtraResponse) {
        this.hasNegativeExtraResponse = hasNegativeExtraResponse;
    }

    public String getNegativeMessage() {
        return negativeMessage;
    }

    public void setNegativeMessage(String negativeMessage) {
        this.negativeMessage = negativeMessage;
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

    public EvaluationPeriodEntity getPeriodId() {
        return periodId;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
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

    public void setOrden(Double orden) {
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


    public QuestionType getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(QuestionType typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public void setPeriodId(EvaluationPeriodEntity periodId) {
        this.periodId = periodId;
    }


    public enum QuestionType {
    	MANDATORY {
            @Override
            public String toString() {
                return "mandatory";
            }
        },
        ADVANCED {
            @Override
            public String toString() {
                return "advanced";
            }
        },
        BASIC {
            @Override
            public String toString() {
                return "basic";
            }
        },
    	RECOMMENDED {
            @Override
            public String toString() {
                return "recommended";
            }
        };
    }

}
