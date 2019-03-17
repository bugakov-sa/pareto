package pareto.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pareto.core.api.AlgorithmController;
import pareto.core.api.ContextController;
import pareto.core.api.PlayController;
import pareto.core.api.RobotController;
import pareto.core.api.dto.*;
import pareto.core.repository.AlgorithmRepository;
import pareto.core.repository.ContextRepository;
import pareto.core.repository.PlayRepository;
import pareto.core.repository.RobotRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoreApplicationTest {

    @Autowired
    private AlgorithmController algorithmController;
    @Autowired
    private AlgorithmRepository algorithmRepository;
    @Autowired
    private RobotController robotController;
    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private ContextController contextController;
    @Autowired
    private ContextRepository contextRepository;
    @Autowired
    private PlayController playController;
    @Autowired
    private PlayRepository playRepository;

    private static final String ALGORITHM_NAME = "Скользящее среднее";
    private static final String ALGORITHM_DESCRIPTION = "Линейное скользящее среднее";
    private static final List<ParamDto> ROBOT_PARAMS = List.of(
            new ParamDto() {{
                setName("product");
                setValue("br-12.19");
            }},
            new ParamDto() {{
                setName("depthCandles");
                setValue("5");
            }}
    );
    private static final String CONTEXT_NAME = "02.19 br-12.19";
    private static final String CONTEXT_DESCRIPTION = "Февраль br-12.19";
    private static final List<ParamDto> CONTEXT_PARAMS = List.of(
            new ParamDto() {{
                setName("product");
                setValue("br-12.19");
            }},
            new ParamDto() {{
                setName("from");
                setValue("01.02.19");
            }},
            new ParamDto() {{
                setName("to");
                setValue("01.03.19");
            }}
    );

    @Test
    public void testAlgorithmApi() {

        AlgorithmDto algorithm = createAlgorithm(ALGORITHM_NAME, ALGORITHM_DESCRIPTION);
        assertNotNull(algorithm);
        assertEquals(ALGORITHM_NAME, algorithm.getName());
        assertEquals(ALGORITHM_DESCRIPTION, algorithm.getDescription());

        checkAlgorithmsEquals(algorithm, algorithmController.getAlgorithm(algorithm.getId()));
    }

    @Test
    public void testRobotApi() {

        AlgorithmDto algorithm = createAlgorithm(ALGORITHM_NAME, ALGORITHM_DESCRIPTION);

        RobotDto robot = createRobot(algorithm.getId(), ROBOT_PARAMS);
        assertNotNull(robot);
        checkAlgorithmsEquals(algorithm, robot.getAlgorithm());
        checkParamsEquals(ROBOT_PARAMS, robot.getParams());

        checkRobotEquals(robot, robotController.getRobot(robot.getId()));
    }

    @Test
    public void testContextApi() {

        ContextDto context = createContext(CONTEXT_NAME, CONTEXT_DESCRIPTION, CONTEXT_PARAMS);
        assertNotNull(context);
        assertEquals(CONTEXT_NAME, context.getName());
        assertEquals(CONTEXT_DESCRIPTION, context.getDescription());
        checkParamsEquals(CONTEXT_PARAMS, context.getParams());

        checkContextEquals(context, contextController.getContext(context.getId()));
    }

    private void checkAlgorithmsEquals(AlgorithmDto expected, AlgorithmDto actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

    private void checkParamsEquals(List<ParamDto> expected, List<ParamDto> actual) {

        assertNotNull(expected);
        assertEquals(expected.size(), actual.size());

        expected = new ArrayList<>(expected);
        Collections.sort(expected, Comparator.comparing(ParamDto::getName));
        actual = new ArrayList<>(expected);
        Collections.sort(actual, Comparator.comparing(ParamDto::getName));

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
            assertEquals(expected.get(i).getValue(), actual.get(i).getValue());
        }
    }

    private void checkRobotEquals(RobotDto expected, RobotDto actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        checkAlgorithmsEquals(expected.getAlgorithm(), actual.getAlgorithm());
        checkParamsEquals(expected.getParams(), actual.getParams());
    }

    private void checkContextEquals(ContextDto expected, ContextDto actual) {
        assertNotNull(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        checkParamsEquals(expected.getParams(), actual.getParams());
    }

    private AlgorithmDto createAlgorithm(String name, String description) {
        NewAlgorithmDto newAlgorithmDto = new NewAlgorithmDto();
        newAlgorithmDto.setName(name);
        newAlgorithmDto.setDescription(description);
        return algorithmController.createAlgorithm(newAlgorithmDto);
    }

    private RobotDto createRobot(long algorithmId, List<ParamDto> params) {
        NewRobotDto newRobotDto = new NewRobotDto();
        newRobotDto.setAlgorithmId(algorithmId);
        newRobotDto.setParams(params);
        return robotController.createRobot(newRobotDto);
    }

    private ContextDto createContext(String name, String description, List<ParamDto> params) {
        NewContextDto newContextDto = new NewContextDto();
        newContextDto.setName(name);
        newContextDto.setDescription(description);
        newContextDto.setParams(params);
        return contextController.createContext(newContextDto);
    }

    @Before
    public void clearDbBeforeTest() {
        clearDb();
    }

    @After
    public void clearDbAfterTest() {
        clearDb();
    }

    private void clearDb() {
        playRepository.deleteAll();
        contextRepository.deleteAll();
        robotRepository.deleteAll();
        algorithmRepository.deleteAll();
    }
}
