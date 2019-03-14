package pareto.core.service;

import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.Play;
import pareto.core.entity.PlayStatus;
import pareto.core.entity.Robot;
import pareto.core.repository.ContextRepository;
import pareto.core.repository.PlayRepository;
import pareto.core.repository.RobotRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        playRepository.save(play);
        PlayStatus playStatus = new PlayStatus();
        playStatus.setPlayId(play.getId());
        playStatus.setStatusCode(0);
        playStatus.setTime(LocalDateTime.now());
        play.setStatus(new ArrayList<>());
        play.getStatus().add(playStatus);
        playRepository.save(play);
        return play;
    }

    public List<Play> getAllPlays() {
        return playRepository.findAll();
    }

    public Optional<Play> getPlay(long id) {
        return playRepository.findById(id);
    }
}
