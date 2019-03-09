package pareto.core.play;

import pareto.core.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public class Context {

    private final PlayMeta playMeta;
    private volatile Portfolio portfolio;
    private volatile Quotation quotation;
    private volatile LocalDateTime time;
    private volatile Candle pnlCandle;

    public Context(PlayMeta playMeta, Portfolio portfolio, Quotation quotation, LocalDateTime time) {
        this.playMeta = playMeta;
        this.portfolio = portfolio;
        this.quotation = quotation;
        this.time = time;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public Quotation getQuotation() {
        return quotation;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public PlayReport getPlayReport() {
        return new PlayReport(playMeta, pnlCandle);
    }

    public Context apply(List<Event> events) {
        return this;
    }
}
