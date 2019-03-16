package pareto.core.play;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pareto.core.entity.Play;
import pareto.core.service.PlayService;

import java.util.List;

@Service
public class PlayProcessStarter {

    private final PlayService playService;
    private final PlayProcessFactory playProcessFactory;

    public PlayProcessStarter(PlayService playService, PlayProcessFactory playProcessFactory) {
        this.playService = playService;
        this.playProcessFactory = playProcessFactory;
    }

    @Scheduled(fixedDelay = 5000)
    public void startNewPlays() {
        List<Play> newPlays = playService.getNewPlays();
        if(newPlays.isEmpty()) {
            return;
        }
        newPlays.forEach(play -> {
            PlayProcess playProcess = playProcessFactory.createPlayProcess(play);
            playProcess.start();
        });
    }
}
