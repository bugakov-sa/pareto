package pareto.core.play;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;

@Service
public class PlayStateFactory {

    public PlayState createPlayState(long playId, Context context) {
        return new PlayState(playId, context.getStartSum());
    }
}
