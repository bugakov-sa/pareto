package pareto.core.api.dto;

import java.util.List;

public class RobotDto {

    private long id;
    private AlgorithmDto algorithm;
    private List<ParamDto> params;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public AlgorithmDto getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(AlgorithmDto algorithm) {
        this.algorithm = algorithm;
    }

    public List<ParamDto> getParams() {
        return params;
    }

    public void setParams(List<ParamDto> params) {
        this.params = params;
    }
}
