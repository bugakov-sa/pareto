package pareto.core.play;

import pareto.core.entity.Quotation;

import java.util.Iterator;

public class Play extends Thread {

    private final Player player;
    private final Iterator<Quotation> quotationIterator;

    private volatile PlayState playState;

    public Play(
            Player player,
            PlayState startPlayState,
            Iterator<Quotation> quotationIterator
    ) {
        this.player = player;
        this.quotationIterator = quotationIterator;
        this.playState = startPlayState;
    }

    @Override
    public void run() {
        while (quotationIterator.hasNext()) {
            Quotation quotation = quotationIterator.next();
            playState = playState.apply(quotation.getEvents());
            playState = playState.apply(player.play(playState));
        }
    }

    public PlayState getPlayState() {
        return playState;
    }
}
