package pareto.core.api.dto;

public class PlayDto {

    private long id;
    private long robotId;
    private long contextId;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
