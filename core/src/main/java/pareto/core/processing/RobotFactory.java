package pareto.core.processing;

import pareto.core.entity.RobotMeta;

import java.util.Collections;

public class RobotFactory {

    public Robot createRobot(RobotMeta robotMeta) {
        return state -> Collections.emptyList();
    }
}
