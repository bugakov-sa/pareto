package pareto.core.player;

import pareto.core.entity.Play;

public interface Player {

    void start();

    boolean isStarted();

    int getProgress();

    Play getPlay();

    PlayerState getState();
}
