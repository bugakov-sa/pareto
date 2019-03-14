package pareto.core.api.dto;

public class PlayDto {

    private long id;
    private RobotDto robot;
    private ContextDto context;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
