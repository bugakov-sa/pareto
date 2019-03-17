package pareto.core.api.dto;

import java.util.List;

public class RobotDto {

    private long id;
    private String className;
    private List<ParamDto> params;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<ParamDto> getParams() {
        return params;
    }

    public void setParams(List<ParamDto> params) {
        this.params = params;
    }
}
