package pareto.core.api.dto;

import pareto.core.entity.PlayStatus;

public class PlayDto {

    private long id;
    private long robotId;
    private long contextId;
    private PlayStatus status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRobotId() {
        return robotId;
    }

    public void setRobotId(long robotId) {
        this.robotId = robotId;
    }

    public long getContextId() {
        return contextId;
    }

    public void setContextId(long contextId) {
        this.contextId = contextId;
    }

    public PlayStatus getStatus() {
        return status;
    }

    public void setStatus(PlayStatus status) {
        this.status = status;
    }
}
