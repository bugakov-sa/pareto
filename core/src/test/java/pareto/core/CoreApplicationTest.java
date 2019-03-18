package pareto.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pareto.core.api.*;
import pareto.core.api.dto.*;

import java.time.LocalDateTime;
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
    private RobotController robotController;
    @Autowired
    private ContextController contextController;
    @Autowired
    private PlayController playController;
    @Autowired
    private ProductController productController;
    @Autowired
    private QuotationController quotationController;

    private static final String ROBOT_CLASS_NAME = "Скользящее среднее";
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
        assertEquals(0, play.getStatus());

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

    private List<QuotationDto> getQuotationSample(long productId) {
        return List.of(
                new QuotationDto() {
                    {
                        setProductId(productId);
                        setTime(LocalDateTime.of(2019, 2, 1, 10, 0, 0));
                        setOpen(1234);
                        setClose(1259);
                        setMin(1230);
                        setMax(1273);
                    }
                },
                new QuotationDto() {
                    {
                        setProductId(productId);
                        setTime(LocalDateTime.of(2019, 2, 1, 10, 1, 0));
                        setOpen(1259);
                        setClose(1284);
                        setMin(1242);
                        setMax(1284);
                    }
                },
                new QuotationDto() {
                    {
                        setProductId(productId);
                        setTime(LocalDateTime.of(2019, 2, 1, 10, 2, 0));
                        setOpen(1284);
                        setClose(1301);
                        setMin(1284);
                        setMax(1312);
                    }
                },
                new QuotationDto() {
                    {
                        setProductId(productId);
                        setTime(LocalDateTime.of(2019, 2, 1, 10, 3, 0));
                        setOpen(1301);
                        setClose(1311);
                        setMin(1299);
                        setMax(1312);
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
}
