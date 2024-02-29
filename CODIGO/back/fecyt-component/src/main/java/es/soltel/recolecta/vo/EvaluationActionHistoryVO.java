package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

public class EvaluationActionHistoryVO {

    private Long id;

    private EvaluationVO evaluationId;
    private ActionVO actionId;
    private UserVO userId;
    private LocalDateTime lastEdited;
    private Integer nDeleteState;

    // Getters
    public Long getId() {
        return id;
    }

    public UserVO getUserId() {
        return userId;
    }

    public void setUserId(UserVO userId) {
        this.userId = userId;
    }

    public EvaluationVO getEvaluationId() {
        return evaluationId;
    }

    public ActionVO getActionId() {
        return actionId;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setEvaluationId(EvaluationVO evaluationId) {
        this.evaluationId = evaluationId;
    }

    public void setActionId(ActionVO actionId) {
        this.actionId = actionId;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
