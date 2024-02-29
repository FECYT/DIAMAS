package es.soltel.recolecta.vo;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class QuestionByYearAndFileDTO {
    private BigInteger questionId;
    private String description;
    private LocalDateTime finishDate;
    private String title;
    private String categoryType;
    private Integer isWritableByEvaluator;
    private String typeQuestion;
    private Float weight;
    private String institution;
    private String userName;
    private String userSurname;
    
    public BigInteger getQuestionId() {
        return questionId;
    }

    public void setQuestionId(BigInteger questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getIsWritableByEvaluator() {
        return isWritableByEvaluator;
    }

    public void setIsWritableByEvaluator(Integer isWritableByEvaluator) {
        this.isWritableByEvaluator = isWritableByEvaluator;
    }

    public String getTypeQuestion() {
        return typeQuestion;
    }

    public void setTypeQuestion(String typeQuestion) {
        this.typeQuestion = typeQuestion;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}
}