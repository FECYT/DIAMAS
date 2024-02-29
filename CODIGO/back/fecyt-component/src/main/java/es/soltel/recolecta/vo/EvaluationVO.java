package es.soltel.recolecta.vo;

import java.time.LocalDateTime;
import java.util.List;

import es.soltel.recolecta.response.RepositoryResponse;

public class EvaluationVO {

    private Long id;

    private UserRepositoryVO userRepository;

    private LocalDateTime lastEdited;

    private String evaluationState;

    private LocalDateTime closeDate;

    private Double evaluationGrade;

    private Integer nDeleteState;

    private List<RepositoryResponse> repositories;
    
    private QuestionnaireTypeVO questionnaireType;

    // Getters and Setters


    public Long getId() {
        return id;
    }

    public List<RepositoryResponse> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryResponse> repositories) {
        this.repositories = repositories;
    }

    public void setId(Long id) {
        this.id = id;
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

	public UserRepositoryVO getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepositoryVO userRepository) {
		this.userRepository = userRepository;
	}

	public QuestionnaireTypeVO getQuestionnaireType() {
		return questionnaireType;
	}

	public void setQuestionnaireType(QuestionnaireTypeVO questionnaireType) {
		this.questionnaireType = questionnaireType;
	}
}
