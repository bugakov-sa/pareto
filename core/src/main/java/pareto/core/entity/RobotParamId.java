package pareto.core.entity;

import java.io.Serializable;

public class RobotParamId implements Serializable {
    private Long robotId;
    private String name;

    public Long getRobotId() {
        return robotId;
    }

    public void setRobotId(Long robotId) {
        this.robotId = robotId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
