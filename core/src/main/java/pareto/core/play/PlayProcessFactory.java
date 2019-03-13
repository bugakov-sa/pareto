package pareto.core.play;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.Play;
import pareto.core.entity.Quotation;
import pareto.core.repository.QuotationRepository;

import java.util.Iterator;

@Service
public class PlayProcessFactory {

    private final PlayerFactory playerFactory;
    private final PlayStateFactory playStateFactory;
    private final QuotationRepository quotationRepository;

    public PlayProcessFactory(
            PlayerFactory playerFactory,
            PlayStateFactory playStateFactory,
            QuotationRepository quotationRepository
    ) {
        this.playerFactory = playerFactory;
        this.playStateFactory = playStateFactory;
        this.quotationRepository = quotationRepository;
    }

    public PlayProcess createPlay(Play play) {
        Player player = playerFactory.createRobot(play.getRobot());
        Context context = play.getContext();
        PlayState startPlayState = playStateFactory.createPlayState(context);
        Iterator<Quotation> quotationIterator = quotationRepository.getQuotationIterator(
                startPlayState.getTime(),
                context.getToTime(),
                context.getProducts()
        );
        return new PlayProcess(player, startPlayState, quotationIterator);
    }
}
