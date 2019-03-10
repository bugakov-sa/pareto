package pareto.core.play;

import pareto.core.entity.Quotation;

import java.util.Iterator;

public class Play extends Thread {

    private final Robot robot;
    private final Iterator<Quotation> quotationIterator;

    private volatile PlayState playState;

    public Play(
            Robot robot,
            PlayState startPlayState,
            Iterator<Quotation> quotationIterator
    ) {
        this.robot = robot;
        this.quotationIterator = quotationIterator;
        this.playState = startPlayState;
    }

    @Override
    public void run() {
        while (quotationIterator.hasNext()) {
            Quotation quotation = quotationIterator.next();
            playState = playState.apply(quotation.getEvents());
            playState = playState.apply(robot.handle(playState));
        }
    }

    public PlayState getPlayState() {
        return playState;
    }
}
