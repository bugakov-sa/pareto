package pareto.core.entity;

public class Play {

    private Long id;
    private Long robotId;
    private Long contextId;
    private PlayStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRobotId() {
        return robotId;
    }

    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    public Long getContextId() {
        return contextId;
    }

    public void setContextId(Long contextId) {
        this.contextId = contextId;
    }

    public PlayStatus getStatus() {
        return status;
    }

    public void setStatus(PlayStatus status) {
        this.status = status;
    }
}
