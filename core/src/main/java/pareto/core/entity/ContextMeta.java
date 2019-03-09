package pareto.core.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ContextMeta {

    private final long id;
    private final String name;
    private final String description;
    private final Map<String, String> params;

    public ContextMeta(long id, String name, String description, Map<String, String> params) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.params = params;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public LocalDateTime getFromTime() {
        return null;
    }

    public LocalDateTime getToTime() {
        return null;
    }

    public List<String> getProducts() {
        return null;
    }
}
