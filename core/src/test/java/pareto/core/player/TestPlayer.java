package pareto.core.player;

import pareto.core.entity.Order;
import pareto.core.entity.Param;
import pareto.core.entity.Position;
import pareto.core.entity.Quotation;
import pareto.core.play.PlayState;
import pareto.core.play.Player;

import java.util.ArrayList;
import java.util.List;

public class TestPlayer implements Player {

    private final List<Param> params;

    public TestPlayer(List<Param> params) {
        this.params = params;
    }

    @Override
    public List<Order> play(PlayState state) {
        ArrayList<Order> res = new ArrayList<>();
        Quotation quotation = state.getQuotations().get(0);
        List<Order> orders = state.getOrders();
        List<Position> positions = state.getPositions();
        if(quotation.getClose() < 1000 && orders.isEmpty() && positions.isEmpty()) {
            res.add(Order.createLong(quotation.getProductId(), quotation.getTime()));
        }
        if(quotation.getClose() > 1050 && !(orders.isEmpty() && positions.isEmpty())) {
            res.add(Order.createShort(quotation.getProductId(), quotation.getTime()));
        }
        return res;
    }
}
