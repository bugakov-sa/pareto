package pareto.core.play;

import pareto.core.entity.Event;

import java.util.List;

public interface Robot {

    List<Event> handle(PlayState state);
}
