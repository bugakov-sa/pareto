package pareto.core.player;

import pareto.core.entity.Order;
import pareto.core.entity.Param;
import pareto.core.play.PlayState;
import pareto.core.play.Player;

import java.util.ArrayList;
import java.util.List;

public class DoNothingPlayer implements Player {

    private final List<Param> params;

    public DoNothingPlayer(List<Param> params) {
        this.params = params;
    }

    @Override
    public List<Order> play(PlayState state) {
        return new ArrayList<>();
    }
}
