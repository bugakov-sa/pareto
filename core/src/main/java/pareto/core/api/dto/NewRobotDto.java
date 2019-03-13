package pareto.core.api.dto;

import java.util.List;

public class NewRobotDto {

    private long algorithmId;
    private List<ParamDto> params;

    public long getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(long algorithmId) {
        this.algorithmId = algorithmId;
    }

    public List<ParamDto> getParams() {
        return params;
    }

    public void setParams(List<ParamDto> params) {
        this.params = params;
    }
}
