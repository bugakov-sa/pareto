package pareto.core.algorithm;

import pareto.core.entity.Order;
import pareto.core.player.PlayerState;

import java.util.List;

public interface Algorithm {

    List<Order> execute(PlayerState state);
}
