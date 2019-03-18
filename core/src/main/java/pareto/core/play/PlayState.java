package pareto.core.play;

import pareto.core.entity.*;

import java.util.ArrayList;
import java.util.List;

public class PlayState {

    private int sum;
    private List<Order> orders = new ArrayList<>();
    private List<Position> positions = new ArrayList<>();
    private List<Quotation> quotations;

    public PlayState(int startSum) {
        sum = startSum;
    }

    public List<Event> applyOrders(List<Order> orders) {
        this.orders.addAll(orders);
        //TODO convert orders to events
        return new ArrayList<>();
    }

    public List<Event> applyQuotations(List<Quotation> quotations) {
        this.quotations = quotations;
        //TODO execute orders and convert executions to events
        return new ArrayList<>();
    }

    public PlayPnl getPlayPnl() {
        //TODO
        return null;
    }
}
