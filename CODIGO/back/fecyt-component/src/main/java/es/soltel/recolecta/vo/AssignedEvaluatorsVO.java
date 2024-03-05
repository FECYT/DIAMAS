package es.soltel.recolecta.vo;


import java.util.List;

public class AssignedEvaluatorsVO {

    private Long id;
    private EvaluationVO evaluation;
    private List<UserVO> users;
    private Integer nDeleteState;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EvaluationVO getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(EvaluationVO evaluation) {
        this.evaluation = evaluation;
    }

    public List<UserVO> getUsers() {
        return users;
    }

    public void setUsers(List<UserVO> users) {
        this.users = users;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }
}
