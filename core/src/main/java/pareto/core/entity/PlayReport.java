package pareto.core.entity;


public class PlayReport {
    private final PlayMeta playMeta;
    private final Candle pnlCandle;

    public PlayReport(PlayMeta playMeta, Candle pnlCandle) {
        this.playMeta = playMeta;
        this.pnlCandle = pnlCandle;
    }

    public PlayMeta getPlayMeta() {
        return playMeta;
    }

    public Candle getPnlCandle() {
        return pnlCandle;
    }
}
