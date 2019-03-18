package pareto.core.play;

import pareto.core.entity.Event;
import pareto.core.entity.Order;
import pareto.core.entity.Quotation;
import pareto.core.service.EventService;
import pareto.core.service.PlayPnlService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayProcess extends Thread {

    private final Player player;
    private final Iterator<List<Quotation>> quotationsIterator;
    private final EventService eventService;
    private final PlayPnlService playPnlService;

    private PlayState playState;

    public PlayProcess(
            Player player,
            PlayState startPlayState,
            Iterator<List<Quotation>> quotationsIterator,
            EventService eventService,
            PlayPnlService playPnlService
    ) {
        this.player = player;
        this.quotationsIterator = quotationsIterator;
        this.playState = startPlayState;
        this.eventService = eventService;
        this.playPnlService = playPnlService;
    }

    @Override
    public void run() {
        while (quotationsIterator.hasNext()) {
            List<Quotation> quotations = quotationsIterator.next();
            List<Event> events = new ArrayList<>();
            events.addAll(playState.applyQuotations(quotations));
            List<Order> orders = player.play(playState);
            events.addAll(playState.applyOrders(orders));
            eventService.save(events);
            playPnlService.save(playState.getPlayPnl());
        }
    }
}
