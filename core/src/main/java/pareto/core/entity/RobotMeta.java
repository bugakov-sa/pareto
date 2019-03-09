package pareto.core.entity;

import java.util.Map;

public class RobotMeta {

    private final long id;
    private final AlgorithmMeta algorithmMeta;
    private final Map<String, String> params;

    public RobotMeta(long id, AlgorithmMeta algorithmMeta, Map<String, String> params) {
        this.id = id;
        this.algorithmMeta = algorithmMeta;
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public AlgorithmMeta getAlgorithmMeta() {
        return algorithmMeta;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
