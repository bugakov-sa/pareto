package pareto.core.processing;

import pareto.core.entity.PlayMeta;

public interface Play {

    void start();

    boolean isStarted();

    int getProgress();

    PlayMeta getMeta();

    PlayState getState();
}
