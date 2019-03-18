package pareto.core.play;

import pareto.core.entity.*;

import java.time.LocalDateTime;
import java.util.List;

public class PlayState {

    private final Play play;
    private volatile Portfolio portfolio;
    private volatile List<Quotation> quotations;
    private volatile LocalDateTime time;
    private volatile Candle pnlCandle;

    public PlayState(Play play, Portfolio portfolio, List<Quotation> quotations, LocalDateTime time) {
        this.play = play;
        this.portfolio = portfolio;
        this.quotations = quotations;
        this.time = time;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public List<Quotation> getQuotation() {
        return quotations;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public PlayReport getPlayReport() {
        return new PlayReport(play, pnlCandle);
    }

    public PlayState applyEvents(List<Event> events) {
        return this;
    }

    public PlayState applyQuotations(List<Quotation> quotations) {
        return this;
    }
}
