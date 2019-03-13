package pareto.core.play;

import pareto.core.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public class PlayState {

    private final Play play;
    private volatile Portfolio portfolio;
    private volatile Quotation quotation;
    private volatile LocalDateTime time;
    private volatile Candle pnlCandle;

    public PlayState(Play play, Portfolio portfolio, Quotation quotation, LocalDateTime time) {
        this.play = play;
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
        return new PlayReport(play, pnlCandle);
    }

    public PlayState apply(List<Event> events) {
        return this;
    }
}
