package pareto.core.play;

import pareto.core.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
        List<Event> res = new ArrayList<>();
        for (Quotation quotation : quotations) {
            List<Order> executedOrders = new ArrayList<>();
            for (Order order : orders) {
                if (order.getProductId() == quotation.getProductId()) {
                    List<Event> executionEvents = executeOrder(order, quotation);
                    if (!executionEvents.isEmpty()) {
                        executedOrders.add(order);
                        res.addAll(executionEvents);
                    }
                }
            }
            orders.removeAll(executedOrders);
        }
        return res;
    }

    private List<Event> executeOrder(Order order, Quotation quotation) {
        Position position = new Position(
                order.getProductId(),
                order.getPositionType(),
                order.getPositionSize(),
                quotation.getOpen()
        );
        List<Position> mergingPositions = positions.stream()
                .filter(p -> p.getProductId() == order.getProductId())
                .collect(Collectors.toList());
        positions.removeAll(mergingPositions);
        positions.addAll(mergePositions(mergingPositions, position));
        ArrayList<Event> res = new ArrayList<>();
        //TODO convert order execution to events
        return res;
    }

    private List<Position> mergePositions(List<Position> mergingPositions, Position newPosition) {
        int sumDiff = 0;
        int newPositionSize = newPosition.getSize();
        List<Position> res = new ArrayList<>();
        for(Position position : mergingPositions) {
            if(position.getType() != newPosition.getType()) {
                sumDiff += (position.getType() == PositionType.LONG ? 1 : -1)
                        * newPositionSize * (newPosition.getOpenPrice() - position.getOpenPrice());
                if(position.getSize() <= newPositionSize) {
                    newPositionSize -= position.getSize();
                }
                else {
                    newPositionSize = 0;
                    res.add(new Position(
                            position.getProductId(),
                            position.getType(),
                            position.getSize() - newPositionSize,
                            position.getOpenPrice()
                    ));
                }
            }
            else {
                res.add(position);
            }
        }
        if(newPositionSize > 0) {
            res.add(new Position(
                    newPosition.getProductId(),
                    newPosition.getType(),
                    newPositionSize,
                    newPosition.getOpenPrice()
            ));
        }
        sum += sumDiff;
        return res;
    }

    public PlayPnl getPlayPnl() {
        int open = sum, close = sum, min = sum, max = sum;
        for(Position position : positions) {
            for(Quotation quotation : quotations) {
                if(position.getProductId() == quotation.getProductId()) {
                    open += getPositionValue(position, quotation, Quotation::getOpen);
                    close += getPositionValue(position, quotation, Quotation::getClose);
                    min += getPositionValue(position, quotation, Quotation::getMin);
                    max += getPositionValue(position, quotation, Quotation::getMax);
                }
            }
        }
        PlayPnl res = new PlayPnl();
        res.setTime(quotations.get(0).getTime());
        res.setOpen(open);
        res.setClose(close);
        res.setMin(min);
        res.setMax(max);
        return res;
    }

    private int getPositionValue(Position position, Quotation quotation, Function<Quotation, Integer> function) {
        return (position.getType() == PositionType.LONG ? 1 : -1)
                * position.getSize() * (function.apply(quotation) - position.getOpenPrice());
    }
}
