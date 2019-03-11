package pareto.core.entity;

import java.util.Map;

public class RobotMeta {

    private final long id;
    private final Algorithm algorithm;
    private final Map<String, String> params;

    public RobotMeta(long id, Algorithm algorithm, Map<String, String> params) {
        this.id = id;
        this.algorithm = algorithm;
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
