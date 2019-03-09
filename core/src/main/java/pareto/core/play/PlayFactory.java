package pareto.core.play;

import org.springframework.stereotype.Service;
import pareto.core.entity.ContextMeta;
import pareto.core.entity.PlayMeta;
import pareto.core.entity.Quotation;
import pareto.core.repository.QuotationRepository;

import java.util.Iterator;

@Service
public class PlayFactory {

    private final RobotFactory robotFactory;
    private final ContextFactory contextFactory;
    private final QuotationRepository quotationRepository;

    public PlayFactory(
            RobotFactory robotFactory,
            ContextFactory contextFactory,
            QuotationRepository quotationRepository
    ) {
        this.robotFactory = robotFactory;
        this.contextFactory = contextFactory;
        this.quotationRepository = quotationRepository;
    }

    public Play createPlay(PlayMeta playMeta) {
        Robot robot = robotFactory.createRobot(playMeta.getRobotMeta());
        ContextMeta contextMeta = playMeta.getContextMeta();
        Context startContext = contextFactory.createContext(contextMeta);
        Iterator<Quotation> quotationIterator = quotationRepository.getQuotationIterator(
                startContext.getTime(),
                contextMeta.getToTime(),
                contextMeta.getProducts()
        );
        return new Play(robot, startContext, quotationIterator);
    }
}
