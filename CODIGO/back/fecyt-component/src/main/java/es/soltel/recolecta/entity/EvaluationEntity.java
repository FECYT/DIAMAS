package es.soltel.recolecta.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "evaluation")
public class EvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "repository_id")
    private UserRepositoryEntity userRepository;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<QuestionnaireEntity> questionnaires = new HashSet<>();

    private LocalDateTime lastEdited;
    private String evaluationState;
    private LocalDateTime closeDate;
    private Double evaluationGrade;
    private Integer nDeleteState;
    
    @ManyToOne
    @JoinColumn(name = "questionnaire_type_id")
    private QuestionnaireTypeEntity questionnaireType;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRepositoryEntity getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepositoryEntity userRepository) {
        this.userRepository = userRepository;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

    public String getEvaluationState() {
        return evaluationState;
    }

    public void setEvaluationState(String evaluationState) {
        this.evaluationState = evaluationState;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(LocalDateTime closeDate) {
        this.closeDate = closeDate;
    }

    public Double getEvaluationGrade() {
        return evaluationGrade;
    }

    public void setEvaluationGrade(Double evaluationGrade) {
        this.evaluationGrade = evaluationGrade;
    }

    public Integer getnDeleteState() {
        return nDeleteState;
    }

    public void setnDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
    
    public QuestionnaireTypeEntity getQuestionnaireType() {
        return questionnaireType;
    }

    public void setQuestionnaireType(QuestionnaireTypeEntity questionnaireType) {
        this.questionnaireType = questionnaireType;
    }
}
