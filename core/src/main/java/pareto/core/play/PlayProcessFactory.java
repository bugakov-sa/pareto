package pareto.core.play;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.Play;
import pareto.core.entity.Quotation;
import pareto.core.entity.Robot;
import pareto.core.service.*;

import java.util.Iterator;
import java.util.List;

@Service
public class PlayProcessFactory {

    private final PlayerFactory playerFactory;
    private final PlayStateFactory playStateFactory;
    private final QuotationService quotationService;
    private final RobotService robotService;
    private final ContextService contextService;
    private final EventService eventService;
    private final PlayPnlService playPnlService;
    private final PlayService playService;

    public PlayProcessFactory(
            PlayerFactory playerFactory,
            PlayStateFactory playStateFactory,
            QuotationService quotationService,
            RobotService robotService,
            ContextService contextService,
            EventService eventService,
            PlayPnlService playPnlService,
            PlayService playService
    ) {
        this.playerFactory = playerFactory;
        this.playStateFactory = playStateFactory;
        this.quotationService = quotationService;
        this.robotService = robotService;
        this.contextService = contextService;
        this.eventService = eventService;
        this.playPnlService = playPnlService;
        this.playService = playService;
    }

    public PlayProcess createPlayProcess(Play play) throws Exception {
        Robot robot = robotService.getRobot(play.getRobotId()).get();
        Player player = playerFactory.createPlayer(robot);
        Context context = contextService.getContext(play.getContextId()).get();
        PlayState startPlayState = playStateFactory.createPlayState(play.getId(), context);
        Iterator<List<Quotation>> quotationIterator = quotationService.getQuotationsIterator(
                context.getProducts(),
                context.getFromTime(),
                context.getToTime()
        );
        return new PlayProcess(player, startPlayState, quotationIterator, eventService, playPnlService, playService);
    }
}
