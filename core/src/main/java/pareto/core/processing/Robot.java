package pareto.core.processing;

import pareto.core.entity.Order;
import pareto.core.entity.RobotMeta;

import java.util.List;

public interface Robot {

    RobotMeta getMeta();

    List<Order> execute(PlayState state);
}
