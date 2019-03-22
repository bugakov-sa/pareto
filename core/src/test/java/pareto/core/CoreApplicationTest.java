package pareto.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import pareto.core.api.*;
import pareto.core.api.dto.*;
import pareto.core.entity.EventType;
import pareto.core.entity.Order;
import pareto.core.entity.PlayStatus;
import pareto.core.entity.PositionType;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoreApplicationTest {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private RobotController robotController;
    @Autowired
    private ContextController contextController;
    @Autowired
    private PlayController playController;
    @Autowired
    private ProductController productController;
    @Autowired
    private QuotationController quotationController;
    @Autowired
    private PlayPnlController playPnlController;
    @Autowired
    private EventController eventController;

    private static final String ROBOT_CLASS_NAME = "pareto.core.player.TestPlayer";
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
                setName("products");
                setValue("12345");
            }},
            new ParamDto() {{
                setName("from");
                setValue("2019-02-01T00:00:00");
            }},
            new ParamDto() {{
                setName("to");
                setValue("2019-03-01T00:00:00");
            }},
            new ParamDto() {{
                setName("start_sum");
                setValue("100000");
            }}
    );
    private static final String PRODUCT_NAME = "br-12.19";

    @Test
    public void testRobotApi() {

        RobotDto robot = createRobot(ROBOT_CLASS_NAME, ROBOT_PARAMS);
        assertNotNull(robot);
        assertEquals(ROBOT_CLASS_NAME, robot.getClassName());
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

    @Test
    public void testPlayApi() {

        RobotDto robot = createRobot(ROBOT_CLASS_NAME, ROBOT_PARAMS);
        ContextDto context = createContext(CONTEXT_NAME, CONTEXT_DESCRIPTION, CONTEXT_PARAMS);

        PlayDto play = createPlay(robot.getId(), context.getId());
        assertNotNull(play);
        assertEquals(robot.getId(), play.getRobotId());
        assertEquals(context.getId(), play.getContextId());
        assertNotNull(play.getStatus());

        checkPlayEquals(play, playController.getPlay(play.getId()));
    }

    @Test
    public void testProductApi() {

        ProductDto product = createProduct(PRODUCT_NAME);
        assertNotNull(product);
        assertEquals(PRODUCT_NAME, product.getName());

        checkProductEquals(product, productController.getProduct(product.getId()));
    }

    @Test
    public void testQuotationApi() {

        ProductDto product = createProduct(PRODUCT_NAME);
        long productId = product.getId();

        List<QuotationDto> quotationDtos = getQuotationSample(productId);
        quotationController.save(quotationDtos);

        checkQuotationsEquals(quotationDtos, quotationController.getQuotations(productId));
    }

    @Test
    public void testPlayCore() throws InterruptedException {

        ProductDto product = createProduct(PRODUCT_NAME);
        long productId = product.getId();

        List<QuotationDto> quotationDtos = getQuotationSample(productId);
        quotationController.save(quotationDtos);

        RobotDto robot = createRobot(ROBOT_CLASS_NAME, ROBOT_PARAMS);
        ContextDto context = createContext(CONTEXT_NAME, CONTEXT_DESCRIPTION, getContextParams(productId));

        PlayDto play = createPlay(robot.getId(), context.getId());

        while (playController.getPlay(play.getId()).getStatus() != PlayStatus.FINISHED) {
            TimeUnit.MILLISECONDS.sleep(100);
        }
        List<PlayPnlDto> playPnls = playPnlController.getPlayPnl(play.getId());
        checkPlayPnlsEquals(getExpectedPlayPnls(), playPnls);

        List<EventDto> playEvents = eventController.getPlayEvents(play.getId());
        checkEventsEquals(getExpectedPlayEvents(play.getId(), productId), playEvents);
    }

    private List<ParamDto> getContextParams(long productId) {
        return List.of(
                new ParamDto() {{
                    setName("products");
                    setValue(String.valueOf(productId));
                }},
                new ParamDto() {{
                    setName("from");
                    setValue("2019-02-01T00:00:00");
                }},
                new ParamDto() {{
                    setName("to");
                    setValue("2019-03-01T00:00:00");
                }},
                new ParamDto() {{
                    setName("start_sum");
                    setValue("100000");
                }}
        );
    }

    private List<QuotationDto> getQuotationSample(long productId) {
        return List.of(
                new QuotationDto(productId, parse("2019-02-10T15:00:00"), 934, 959, 930, 973),
                new QuotationDto(productId, parse("2019-02-10T15:01:00"), 959, 984, 942, 984),
                new QuotationDto(productId, parse("2019-02-10T15:02:00"), 984, 1001, 984, 1012),
                new QuotationDto(productId, parse("2019-02-10T15:03:00"), 1001, 1051, 1000, 1092),
                new QuotationDto(productId, parse("2019-02-10T15:04:00"), 1051, 1120, 1000, 1163),
                new QuotationDto(productId, parse("2019-02-10T15:05:00"), 1120, 1005, 1005, 1130),
                new QuotationDto(productId, parse("2019-02-10T15:06:00"), 1005, 902, 902, 1005),
                new QuotationDto(productId, parse("2019-02-10T15:07:00"), 902, 999, 900, 1010),
                new QuotationDto(productId, parse("2019-02-10T15:08:00"), 999, 1001, 990, 1002),
                new QuotationDto(productId, parse("2019-02-10T15:09:00"), 1001, 1051, 1000, 1092)
        );
    }

    private List<PlayPnlDto> getExpectedPlayPnls() {
        return List.of(
                //positions 0 orders 0 - positions 0 orders 1
                new PlayPnlDto(parse("2019-02-10T15:00:00"), 100000, 100000, 100000, 100000),
                //positions 0 orders 1 - positions 1 orders 0
                new PlayPnlDto(parse("2019-02-10T15:01:00"), 100000, 100025, 99983, 100025),
                //positions 1 orders 0 - positions 1 orders 0
                new PlayPnlDto(parse("2019-02-10T15:02:00"), 100025, 100042, 100025, 100053),
                //positions 1 orders 0 - positions 1 orders 1
                new PlayPnlDto(parse("2019-02-10T15:03:00"), 100042, 100092, 100041, 100133),
                //positions 1 orders 1 - positions 0 orders 0
                new PlayPnlDto(parse("2019-02-10T15:04:00"), 100092, 100092, 100092, 100092),
                //positions 0 orders 0 - positions 0 orders 0
                new PlayPnlDto(parse("2019-02-10T15:05:00"), 100092, 100092, 100092, 100092),
                //positions 0 orders 0 - positions 0 orders 1
                new PlayPnlDto(parse("2019-02-10T15:06:00"), 100092, 100092, 100092, 100092),
                //positions 0 orders 1 - positions 1 orders 0
                new PlayPnlDto(parse("2019-02-10T15:07:00"), 100092, 100189, 100090, 100200),
                //positions 1 orders 0 - positions 1 orders 0
                new PlayPnlDto(parse("2019-02-10T15:08:00"), 100189, 100191, 100180, 100192),
                //positions 1 orders 0- positions 1 orders 1
                new PlayPnlDto(parse("2019-02-10T15:09:00"), 100191, 100241, 100190, 100282)
        );
    }

    private List<EventDto> getExpectedPlayEvents(long playId, long productId) {
        return List.of(
                new EventDto() {
                    {
                        setTime(parse("2019-02-10T15:00:00"));
                        setPlayId(playId);
                        setEventType(EventType.NEW_ORDER);
                        setParams(List.of(
                                new ParamDto(Order.PARAM_PRODUCT, String.valueOf(productId)),
                                new ParamDto(Order.PARAM_POSITION_TYPE, String.valueOf(PositionType.LONG)),
                                new ParamDto(Order.PARAM_POSITION_SIZE, "1")
                        ));
                    }
                },
                new EventDto() {
                    {
                        setTime(parse("2019-02-10T15:01:00"));
                        setPlayId(playId);
                        setEventType(EventType.ORDER_EXECUTION);
                        setParams(List.of(
                                new ParamDto(Order.PARAM_PRODUCT, String.valueOf(productId)),
                                new ParamDto(Order.PARAM_POSITION_TYPE, String.valueOf(PositionType.LONG)),
                                new ParamDto(Order.PARAM_POSITION_SIZE, "1"),
                                new ParamDto(Order.PARAM_POSITION_OPEN_PRICE, "959")
                        ));
                    }
                },
                new EventDto() {
                    {
                        setTime(parse("2019-02-10T15:03:00"));
                        setPlayId(playId);
                        setEventType(EventType.NEW_ORDER);
                        setParams(List.of(
                                new ParamDto(Order.PARAM_PRODUCT, String.valueOf(productId)),
                                new ParamDto(Order.PARAM_POSITION_TYPE, String.valueOf(PositionType.SHORT)),
                                new ParamDto(Order.PARAM_POSITION_SIZE, "1")
                        ));
                    }
                },
                new EventDto() {
                    {
                        setTime(parse("2019-02-10T15:04:00"));
                        setPlayId(playId);
                        setEventType(EventType.ORDER_EXECUTION);
                        setParams(List.of(
                                new ParamDto(Order.PARAM_PRODUCT, String.valueOf(productId)),
                                new ParamDto(Order.PARAM_POSITION_TYPE, String.valueOf(PositionType.SHORT)),
                                new ParamDto(Order.PARAM_POSITION_SIZE, "1"),
                                new ParamDto(Order.PARAM_POSITION_OPEN_PRICE, "1051")
                        ));
                    }
                },
                new EventDto() {
                    {
                        setTime(parse("2019-02-10T15:06:00"));
                        setPlayId(playId);
                        setEventType(EventType.NEW_ORDER);
                        setParams(List.of(
                                new ParamDto(Order.PARAM_PRODUCT, String.valueOf(productId)),
                                new ParamDto(Order.PARAM_POSITION_TYPE, String.valueOf(PositionType.LONG)),
                                new ParamDto(Order.PARAM_POSITION_SIZE, "1")
                        ));
                    }
                },
                new EventDto() {
                    {
                        setTime(parse("2019-02-10T15:07:00"));
                        setPlayId(playId);
                        setEventType(EventType.ORDER_EXECUTION);
                        setParams(List.of(
                                new ParamDto(Order.PARAM_PRODUCT, String.valueOf(productId)),
                                new ParamDto(Order.PARAM_POSITION_TYPE, String.valueOf(PositionType.LONG)),
                                new ParamDto(Order.PARAM_POSITION_SIZE, "1"),
                                new ParamDto(Order.PARAM_POSITION_OPEN_PRICE, "902")
                        ));
                    }
                },
                new EventDto() {
                    {
                        setTime(parse("2019-02-10T15:09:00"));
                        setPlayId(playId);
                        setEventType(EventType.NEW_ORDER);
                        setParams(List.of(
                                new ParamDto(Order.PARAM_PRODUCT, String.valueOf(productId)),
                                new ParamDto(Order.PARAM_POSITION_TYPE, String.valueOf(PositionType.SHORT)),
                                new ParamDto(Order.PARAM_POSITION_SIZE, "1")
                        ));
                    }
                }
        );
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
        assertEquals(expected.getClassName(), actual.getClassName());
        checkParamsEquals(expected.getParams(), actual.getParams());
    }

    private void checkContextEquals(ContextDto expected, ContextDto actual) {
        assertNotNull(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getDescription(), actual.getDescription());
        checkParamsEquals(expected.getParams(), actual.getParams());
    }

    private void checkPlayEquals(PlayDto expected, PlayDto actual) {
        assertNotNull(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getRobotId(), actual.getRobotId());
        assertEquals(expected.getContextId(), actual.getContextId());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

    private void checkProductEquals(ProductDto expected, ProductDto actual) {
        assertNotNull(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    private void checkQuotationsEquals(List<QuotationDto> expected, List<QuotationDto> actual) {
        assertNotNull(expected);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            QuotationDto expectedQuotation = expected.get(i);
            QuotationDto actualQuotation = actual.get(i);
            assertEquals(expectedQuotation.getProductId(), actualQuotation.getProductId());
            assertEquals(expectedQuotation.getTime(), actualQuotation.getTime());
            assertEquals(expectedQuotation.getOpen(), actualQuotation.getOpen());
            assertEquals(expectedQuotation.getClose(), actualQuotation.getClose());
            assertEquals(expectedQuotation.getMin(), actualQuotation.getMin());
            assertEquals(expectedQuotation.getMax(), actualQuotation.getMax());
        }
    }

    private void checkPlayPnlsEquals(List<PlayPnlDto> expected, List<PlayPnlDto> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals("i = " + i, expected.get(i).getTime(), actual.get(i).getTime());
            assertEquals("i = " + i, expected.get(i).getOpen(), actual.get(i).getOpen());
            assertEquals("i = " + i, expected.get(i).getClose(), actual.get(i).getClose());
            assertEquals("i = " + i, expected.get(i).getMin(), actual.get(i).getMin());
            assertEquals("i = " + i, expected.get(i).getMax(), actual.get(i).getMax());
        }
    }

    private void checkEventsEquals(List<EventDto> expected, List<EventDto> actual) {
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            checkEventsEquals(i, expected.get(i), actual.get(i));
        }
    }

    private void checkEventsEquals(int i, EventDto expected, EventDto actual) {
        assertEquals("event i = " + i, expected.getPlayId(), actual.getPlayId());
        assertEquals("event i = " + i, expected.getEventType(), actual.getEventType());
        assertEquals("event i = " + i, expected.getTime(), actual.getTime());
        checkParamsEquals(expected.getParams(), actual.getParams());
    }

    private RobotDto createRobot(String className, List<ParamDto> params) {
        NewRobotDto newRobotDto = new NewRobotDto();
        newRobotDto.setClassName(className);
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

    private PlayDto createPlay(long robotId, long contextId) {
        NewPlayDto newPlayDto = new NewPlayDto();
        newPlayDto.setRobotId(robotId);
        newPlayDto.setContextId(contextId);
        return playController.createPlay(newPlayDto);
    }

    private ProductDto createProduct(String name) {
        NewProductDto newProductDto = new NewProductDto();
        newProductDto.setName(name);
        return productController.createProduct(newProductDto);
    }

    @Before
    @After
    public void clearTables() {
        namedParameterJdbcTemplate.update("delete from product_quotation", Map.of());
        namedParameterJdbcTemplate.update("delete from product", Map.of());
        namedParameterJdbcTemplate.update("delete from play_pnl", Map.of());
        namedParameterJdbcTemplate.update("delete from event_param", Map.of());
        namedParameterJdbcTemplate.update("delete from play_event", Map.of());
        namedParameterJdbcTemplate.update("delete from play", Map.of());
        namedParameterJdbcTemplate.update("delete from context_param", Map.of());
        namedParameterJdbcTemplate.update("delete from context", Map.of());
        namedParameterJdbcTemplate.update("delete from robot_param", Map.of());
        namedParameterJdbcTemplate.update("delete from robot", Map.of());
    }
}
