package pareto.core.service;

import org.springframework.stereotype.Service;
import pareto.core.entity.Algorithm;
import pareto.core.entity.Robot;
import pareto.core.entity.RobotParam;
import pareto.core.repository.AlgorithmRepository;
import pareto.core.repository.RobotRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RobotService {

    private final AlgorithmRepository algorithmRepository;
    private final RobotRepository robotRepository;

    public RobotService(AlgorithmRepository algorithmRepository, RobotRepository robotRepository) {
        this.algorithmRepository = algorithmRepository;
        this.robotRepository = robotRepository;
    }

    public Robot createRobot(long algorithmId, List<RobotParam> params) {
        Optional<Algorithm> algorithm = algorithmRepository.findById(algorithmId);
        if (algorithm.isEmpty()) {
            //TODO
        }
        Robot robot = new Robot();
        robot.setAlgorithm(algorithm.get());
        robotRepository.save(robot);
        params.forEach(param -> param.setRobotId(robot.getId()));
        robot.setParams(params);
        robotRepository.save(robot);
        return robot;
    }

    public List<Robot> getAllRobots() {
        return robotRepository.findAll();
    }

    public Optional<Robot> getRobot(long id) {
        return robotRepository.findById(id);
    }
}
