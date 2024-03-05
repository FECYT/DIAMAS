package es.soltel.recolecta.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
@Entity
@Table(name = "system_logs")
public class SystemLogsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Column(name = "source_ip")
    private String sourceIp;

    private String status;

    @Column(name = "n_delete_state")
    private Integer nDeleteState;

    @Column(name = "n_repositories")
    private Integer numRepositories;

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

