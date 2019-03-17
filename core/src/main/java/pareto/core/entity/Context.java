package pareto.core.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Context {

    private Long id;
    private String name;
    private String description;
    private List<Param> params;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Param> getParams() {
        return params;
    }

    public void setParams(List<Param> params) {
        this.params = params;
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
