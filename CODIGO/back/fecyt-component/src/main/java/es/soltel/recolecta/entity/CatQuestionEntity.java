package es.soltel.recolecta.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_questions")
public class CatQuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_type")
    private String categoryType;
    
    @Column(name = "tooltip_type")
    private String tooltipType;

    @Column(name = "ndelete_state")
    private Integer nDeleteState;

    @Column(name = "orden")
    private Double orden;

    @OneToMany(mappedBy = "catQuestion", cascade = CascadeType.REMOVE)
    private List<QuestionEntity> questions = new ArrayList<>();

    @OneToMany(mappedBy = "catQuestion")
    private List<InitialQuestionEntity> initialQuestions = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "questionnaire_type_id")
    private QuestionnaireTypeEntity questionnaireType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryType() {
        return categoryType;
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

    public void setOrden(Double order) {
        this.orden = order;
    }

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }
    
    public QuestionnaireTypeEntity getQuestionnaireType() {
        return questionnaireType;
    }

    public void setQuestionnaireType(QuestionnaireTypeEntity questionnaireType) {
        this.questionnaireType = questionnaireType;
    }

}
