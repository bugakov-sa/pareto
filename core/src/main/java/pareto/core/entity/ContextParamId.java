package pareto.core.entity;

import java.io.Serializable;

public class ContextParamId implements Serializable {
    private Long contextId;
    private String name;

    public Long getContextId() {
        return contextId;
    }

    public void setContextId(Long contextId) {
        this.contextId = contextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
