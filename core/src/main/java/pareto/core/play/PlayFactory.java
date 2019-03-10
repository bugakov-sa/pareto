package pareto.core.play;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.PlayMeta;
import pareto.core.entity.Quotation;
import pareto.core.repository.QuotationRepository;

import java.util.Iterator;

@Service
public class PlayFactory {

    private final RobotFactory robotFactory;
    private final PlayStateFactory playStateFactory;
    private final QuotationRepository quotationRepository;

    public PlayFactory(
            RobotFactory robotFactory,
            PlayStateFactory playStateFactory,
            QuotationRepository quotationRepository
    ) {
        this.robotFactory = robotFactory;
        this.playStateFactory = playStateFactory;
        this.quotationRepository = quotationRepository;
    }

    public Play createPlay(PlayMeta playMeta) {
        Robot robot = robotFactory.createRobot(playMeta.getRobotMeta());
        Context context = playMeta.getContext();
        PlayState startPlayState = playStateFactory.createPlayState(context);
        Iterator<Quotation> quotationIterator = quotationRepository.getQuotationIterator(
                startPlayState.getTime(),
                context.getToTime(),
                context.getProducts()
        );
        return new Play(robot, startPlayState, quotationIterator);
    }
}
