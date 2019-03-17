package pareto.core.play;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pareto.core.service.PlayService;

@Service
public class PlayProcessStarter {

    private static final Logger log = LoggerFactory.getLogger(PlayProcessStarter.class);

    private final PlayService playService;
    private final PlayProcessFactory playProcessFactory;

    public PlayProcessStarter(PlayService playService, PlayProcessFactory playProcessFactory) {
        this.playService = playService;
        this.playProcessFactory = playProcessFactory;
    }

    @Scheduled(fixedDelay = 5000)
    public void startNewPlays() {
        playService.getNewPlays().forEach(play -> {
            Long playId = play.getId();
            try {
                PlayProcess playProcess = playProcessFactory.createPlayProcess(play);
                playProcess.start();
                log.info("Play {} - Started play process", playId);
                playService.updatePlayStatus(playId, 1);
            } catch (Throwable e) {
                log.error("Play = {} - Error during starting play process", playId, e);
                playService.updatePlayStatus(playId, 0);
            }
        });
    }
}
