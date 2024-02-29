package es.soltel.recolecta.vo;

import java.time.LocalDateTime;

public class SystemLogsVO {
    private Long id;
    private String userId;
    private String actionType;
    private LocalDateTime dateTime;
    private String sourceIp;
    private String status;
    private Integer nDeleteState;
    private Integer numRepositories;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getNDeleteState() {
        return nDeleteState;
    }

    public void setNDeleteState(Integer nDeleteState) {
        this.nDeleteState = nDeleteState;
    }

    public Integer getNumRepositories() {
        return numRepositories;
    }

    public void setNumRepositories(Integer numRepositories) {
        this.numRepositories = numRepositories;
    }
}

