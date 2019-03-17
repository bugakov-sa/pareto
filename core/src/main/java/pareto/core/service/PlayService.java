package pareto.core.service;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.Play;
import pareto.core.entity.Robot;
import pareto.core.repository.ContextRepository;
import pareto.core.repository.PlayRepository;
import pareto.core.repository.RobotRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PlayService {

    private final RobotRepository robotRepository;
    private final ContextRepository contextRepository;
    private final PlayRepository playRepository;

    public PlayService(RobotRepository robotRepository, ContextRepository contextRepository, PlayRepository playRepository) {
        this.robotRepository = robotRepository;
        this.contextRepository = contextRepository;
        this.playRepository = playRepository;
    }

    public Play createPlay(long robotId, long contextId) {
        Play play = new Play();
        Optional<Robot> robot = robotRepository.findById(robotId);
        if(robot.isEmpty()) {
            //TODO
        }
        Optional<Context> context = contextRepository.findById(contextId);
        if(context.isEmpty()) {
            //TODO
        }
        play.setRobot(robot.get());
        play.setContext(context.get());
        play.setStatus(0);
        playRepository.save(play);
        return play;
    }

    public void updatePlayStatus(long id, int status) {
        Optional<Play> play = playRepository.findById(id);
        if(play.isEmpty()) {
            //TODO
        }
        play.get().setStatus(status);
        playRepository.save(play.get());
    }

    public List<Play> getAllPlays() {
        return playRepository.findAll();
    }

    public Optional<Play> getPlay(long id) {
        return playRepository.findById(id);
    }

    public List<Play> getNewPlays() {
        return playRepository.findByStatus(0);
    }
}
