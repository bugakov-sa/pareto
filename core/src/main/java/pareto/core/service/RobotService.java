package pareto.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import pareto.core.entity.Param;
import pareto.core.entity.Robot;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RobotService {

    private static final String INSERT_ROBOT_PARAM = "insert into robot_param(robot_id, name, value) values(:robot_id, :name, :value)";
    private static final String SELECT_ALL_ROBOTS = "select robot.id as robot_id, robot.class_name as robot_class_name, robot_param.name as param_name, robot_param.value as param_value from robot join robot_param on robot.id=robot_param.robot_id order by robot.id";
    private static final String SELECT_ROBOT_BY_ID = "select robot.id as robot_id, robot.class_name as robot_class_name, robot_param.name as param_name, robot_param.value as param_value from robot join robot_param on robot.id=robot_param.robot_id where robot.id = :robot_id order by robot.id";

    private final String schemaName;
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RobotService(
            @Value("${spring.flyway.schemas}") String schemaName,
            DataSource dataSource,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.schemaName = schemaName;
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Robot createRobot(String className, List<Param> params) {

        SimpleJdbcInsert robotInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName(schemaName)
                .withTableName("robot")
                .usingColumns("class_name")
                .usingGeneratedKeyColumns("id");
        Map<String, String> contextInsertParams = Map.of(
                "class_name", className
        );
        long robotId = robotInsert.executeAndReturnKey(contextInsertParams).longValue();

        Map<String, ?>[] robotParamInsertParams = new Map[params.size()];
        for (int i = 0; i < params.size(); i++) {
            robotParamInsertParams[i] = Map.of(
                    "robot_id", robotId,
                    "name", params.get(i).getName(),
                    "value", params.get(i).getValue()
            );
        }
        namedParameterJdbcTemplate.batchUpdate(INSERT_ROBOT_PARAM, robotParamInsertParams);

        return getRobot(robotId).get();
    }


    public List<Robot> getAllRobots() {
        List<Robot> robots = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_ALL_ROBOTS, getRowCallbackHandler(robots));
        return robots;
    }

    private RowCallbackHandler getRowCallbackHandler(List<Robot> robots) {
        return new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Robot currRobot = buildrobot(rs);
                if (robots.isEmpty()) {
                    robots.add(currRobot);
                } else {
                    Robot lastContext = robots.get(robots.size() - 1);
                    if (!currRobot.getId().equals(lastContext.getId())) {
                        robots.add(currRobot);
                    }
                }
                currRobot = robots.get(robots.size() - 1);
                currRobot.getParams().add(buildParam(rs));
            }

            private Robot buildrobot(ResultSet rs) throws SQLException {
                Robot robot = new Robot();
                robot.setId(rs.getLong("robot_id"));
                robot.setClassName(rs.getString("robot_class_name"));
                robot.setParams(new ArrayList<>());
                return robot;
            }

            private Param buildParam(ResultSet rs) throws SQLException {
                Param param = new Param();
                param.setName(rs.getString("param_name"));
                param.setValue(rs.getString("param_value"));
                return param;
            }
        };
    }

    public Optional<Robot> getRobot(long id) {
        List<Robot> robots = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_ROBOT_BY_ID, Map.of("robot_id", id), getRowCallbackHandler(robots));
        return robots.isEmpty() ? Optional.empty() : Optional.of(robots.get(0));
    }
}
