package pareto.core.play;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.Play;
import pareto.core.entity.Quotation;
import pareto.core.entity.Robot;
import pareto.core.repository.QuotationRepository;
import pareto.core.service.ContextService;
import pareto.core.service.RobotService;

import java.util.Iterator;

@Service
public class PlayProcessFactory {

    private final PlayerFactory playerFactory;
    private final PlayStateFactory playStateFactory;
    private final QuotationRepository quotationRepository;
    private final RobotService robotService;
    private final ContextService contextService;

    public PlayProcessFactory(
            PlayerFactory playerFactory,
            PlayStateFactory playStateFactory,
            QuotationRepository quotationRepository,
            RobotService robotService,
            ContextService contextService
    ) {
        this.playerFactory = playerFactory;
        this.playStateFactory = playStateFactory;
        this.quotationRepository = quotationRepository;
        this.robotService = robotService;
        this.contextService = contextService;
    }

    public PlayProcess createPlayProcess(Play play) {
        Robot robot = robotService.getRobot(play.getRobotId()).get();
        Player player = playerFactory.createPlayer(robot);
        Context context = contextService.getContext(play.getContextId()).get();
        PlayState startPlayState = playStateFactory.createPlayState(context);
        Iterator<Quotation> quotationIterator = quotationRepository.getQuotationIterator(
                context.getFromTime(),
                context.getToTime(),
                context.getProducts()
        );
        return new PlayProcess(player, startPlayState, quotationIterator);
    }
}
