package pareto.core.entity;

import java.util.Map;

public class Robot {

    private final Long id;
    private final Algorithm algorithm;
    private final Map<String, String> params;

    public Robot(Long id, Algorithm algorithm, Map<String, String> params) {
        this.id = id;
        this.algorithm = algorithm;
        this.params = params;
    }

    public Long getId() {
        return id;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
