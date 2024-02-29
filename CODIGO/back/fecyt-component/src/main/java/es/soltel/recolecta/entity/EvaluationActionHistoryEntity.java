package es.soltel.recolecta.entity;

import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
public class EvaluationActionHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private EvaluationEntity evaluationId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    private ActionEntity actionId;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    private LocalDateTime lastEdited;

    private Integer nDeleteState;

    // Getters
    public Long getId() {
        return id;
    }

    public EvaluationEntity getEvaluationId() {
        return evaluationId;
    }

    public ActionEntity getActionId() {
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

    public void setEvaluationId(EvaluationEntity evaluationId) {
        this.evaluationId = evaluationId;
    }

    public void setActionId(ActionEntity actionId) {
        this.actionId = actionId;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
