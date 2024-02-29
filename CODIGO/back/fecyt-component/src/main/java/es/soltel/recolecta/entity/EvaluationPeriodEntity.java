package es.soltel.recolecta.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evaluation_period")
public class EvaluationPeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "finish_date", nullable = true)
    private LocalDateTime finishDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "delete_state", nullable = false)
    private Integer deleteState;

    @OneToMany(mappedBy = "periodId", cascade = CascadeType.REMOVE)
    private List<QuestionEntity> questions = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "questionnaire_type_id")
    private QuestionnaireTypeEntity questionnaireType;

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
    
    public QuestionnaireTypeEntity getQuestionnaireType() {
        return questionnaireType;
    }

    public void setQuestionnaireType(QuestionnaireTypeEntity questionnaireType) {
        this.questionnaireType = questionnaireType;
    }
}
