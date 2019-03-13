package pareto.core.entity;


public class PlayReport {
    private final Play play;
    private final Candle pnlCandle;

    public PlayReport(Play play, Candle pnlCandle) {
        this.play = play;
        this.pnlCandle = pnlCandle;
    }

    public Play getPlay() {
        return play;
    }

    public Candle getPnlCandle() {
        return pnlCandle;
    }
}
