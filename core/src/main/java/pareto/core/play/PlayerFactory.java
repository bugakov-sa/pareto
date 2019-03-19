package pareto.core.play;

import org.springframework.stereotype.Service;
import pareto.core.entity.Robot;

import java.lang.reflect.Constructor;
import java.util.List;

@Service
public class PlayerFactory {

    public Player createPlayer(Robot robot) throws Exception {
        Class<?> playerClass = Class.forName(robot.getClassName());
        Constructor<?> playerConstructor = playerClass.getConstructor(List.class);
        Player player = (Player)playerConstructor.newInstance(robot.getParams());
        return player;
    }
}
