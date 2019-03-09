package pareto.core.entity;

import java.util.List;

public class Quotation {

    private final List<Event> events;

    public Quotation(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
