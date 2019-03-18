package pareto.core.play;

import pareto.core.entity.Quotation;

import java.util.Iterator;
import java.util.List;

public class PlayProcess extends Thread {

    private final Player player;
    private final Iterator<List<Quotation>> quotationsIterator;

    private volatile PlayState playState;

    public PlayProcess(
            Player player,
            PlayState startPlayState,
            Iterator<List<Quotation>> quotationsIterator
    ) {
        this.player = player;
        this.quotationsIterator = quotationsIterator;
        this.playState = startPlayState;
    }

    @Override
    public void run() {
        while (quotationsIterator.hasNext()) {
            List<Quotation> quotations = quotationsIterator.next();
            playState = playState.applyQuotations(quotations);
            playState = playState.applyEvents(player.play(playState));
        }
    }

    public PlayState getPlayState() {
        return playState;
    }
}
