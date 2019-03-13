package pareto.core.api;

import org.springframework.web.bind.annotation.*;
import pareto.core.api.dto.NewRobotDto;
import pareto.core.api.dto.RobotDto;
import pareto.core.api.dto.mapper.MappingUtil;
import pareto.core.entity.Robot;
import pareto.core.entity.RobotParam;
import pareto.core.service.RobotService;

import java.util.List;
import java.util.Optional;

import static pareto.core.api.dto.mapper.MappingUtil.mapToRobotParams;

@RestController
public class RobotController {

    private final RobotService robotService;

    public RobotController(RobotService robotService) {
        this.robotService = robotService;
    }

    @PostMapping("/robot")
    public RobotDto createRobot(@RequestBody NewRobotDto dto) {
        List<RobotParam> robotParams = mapToRobotParams(dto.getParams());
        Robot robot = robotService.createRobot(dto.getAlgorithmId(), robotParams);
        return MappingUtil.map(robot);
    }



    @GetMapping("/robot")
    public List<RobotDto> getAllRobots() {
        List<Robot> robots = robotService.getAllRobots();
        return MappingUtil.mapRobots(robots);
    }

    @GetMapping("/robot/{id}")
    public RobotDto getRobot(@PathVariable long id) {
        Optional<Robot> robot = robotService.getRobot(id);
        return robot.map(MappingUtil::map).orElse(null);
    }
}
