package pareto.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pareto.core.api.AlgorithmController;
import pareto.core.api.dto.AlgorithmDto;
import pareto.core.api.dto.NewAlgorithmDto;
import pareto.core.repository.AlgorithmRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoreApplicationTest {

    @Autowired
    private AlgorithmController algorithmController;
    @Autowired
    private AlgorithmRepository algorithmRepository;

    @Before
    public void clearDbBeforeTest() {
        algorithmRepository.deleteAll();
    }

    @After
    public void clearDbAfterTest() {
        algorithmRepository.deleteAll();
    }

    @Test
    public void testAlgorithm() throws Exception {

        final String name = "Скользящее среднее";
        final String description = "Линейное скользящее среднее";

        NewAlgorithmDto newAlgorithmDto = new NewAlgorithmDto();
        newAlgorithmDto.setName(name);
        newAlgorithmDto.setDescription(description);

        AlgorithmDto algorithm = algorithmController.createAlgorithm(newAlgorithmDto);

        assertNotNull(algorithm);
        assertEquals(name, algorithm.getName());
        assertEquals(description, algorithm.getDescription());

        long id = algorithm.getId();

        algorithm = algorithmController.getAlgorithm(id);

        assertNotNull(algorithm);
        assertEquals(id, algorithm.getId());
        assertEquals(name, algorithm.getName());
        assertEquals(description, algorithm.getDescription());
    }
}
