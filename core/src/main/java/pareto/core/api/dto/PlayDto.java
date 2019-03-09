package pareto.core.api.dto;

public class PlayDto {

    private long id;
    private RobotDto robot;
    private ContextDto context;
    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RobotDto getRobot() {
        return robot;
    }

    public void setRobot(RobotDto robot) {
        this.robot = robot;
    }

    public ContextDto getContext() {
        return context;
    }

    public void setContext(ContextDto context) {
        this.context = context;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
