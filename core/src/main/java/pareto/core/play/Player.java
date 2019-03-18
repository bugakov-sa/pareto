package pareto.core.play;

import pareto.core.entity.Order;

import java.util.List;

public interface Player {

    List<Order> play(PlayState state);
}
