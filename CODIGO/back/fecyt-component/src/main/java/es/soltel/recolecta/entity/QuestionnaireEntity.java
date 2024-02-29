package es.soltel.recolecta.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "questionnaire_entity")
public class QuestionnaireEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "evaluation_id", nullable = false)
    private EvaluationEntity evaluation;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "questionnaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<QuestionnaireQuestionEntity> questionnaireQuestions = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private EvaluationPeriodEntity period;

    @Column(name = "delete_state", nullable = true)
    private Integer nDeleteState;


    @OneToOne(mappedBy = "questionnaire", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private QuestionnaireToCloseEntity questionnaireToClose;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvaluationEntity getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationEntity evaluation) {
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

    public EvaluationPeriodEntity getPeriod() {
        return period;
    }

    public void setPeriod(EvaluationPeriodEntity period) {
        this.period = period;
    }

    public Integer getnDeleteState() {
        return nDeleteState;
    }

    public void setnDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
