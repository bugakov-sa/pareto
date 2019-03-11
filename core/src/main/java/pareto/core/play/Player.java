package pareto.core.play;

import pareto.core.entity.Event;

import java.util.List;

public interface Player {

    List<Event> play(PlayState state);
}
