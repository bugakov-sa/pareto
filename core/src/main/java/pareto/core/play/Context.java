package pareto.core.play;

import pareto.core.entity.Event;
import pareto.core.entity.Portfolio;
import pareto.core.entity.Quotation;

import java.time.LocalDateTime;
import java.util.List;

public class Context {

    private final Portfolio portfolio;
    private final Quotation quotation;
    private final LocalDateTime time;

    public Context(Portfolio portfolio, Quotation quotation, LocalDateTime time) {
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

    public Context apply(List<Event> events) {
        return this;
    }
}
