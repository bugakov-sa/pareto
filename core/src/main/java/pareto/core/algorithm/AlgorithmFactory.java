package pareto.core.algorithm;

import pareto.core.entity.Robot;

import java.util.Collections;

public class AlgorithmFactory {

    public Algorithm createAlgorithm(Robot robot) {
        return state -> Collections.emptyList();
    }
}
