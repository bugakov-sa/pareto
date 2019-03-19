package pareto.core.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Context {

    private static final String PARAM_NAME_PRODUCTS = "products";
    private static final String PARAM_NAME_FROM_TIME = "from";
    private static final String PARAM_NAME_TO_TIME = "to";
    private static final String PARAM_NAME_START_SUM = "start_sum";

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

    private String getParamValue(String paramName) {
        return params.stream()
                .filter(param -> param.getName().equals(paramName))
                .map(Param::getValue)
                .findFirst()
                .orElse(null);
    }

    public LocalDateTime getFromTime() {
        return LocalDateTime.parse(getParamValue(PARAM_NAME_FROM_TIME));
    }

    public LocalDateTime getToTime() {
        return LocalDateTime.parse(getParamValue(PARAM_NAME_TO_TIME));
    }

    public List<Long> getProducts() {
        return Stream.of(getParamValue(PARAM_NAME_PRODUCTS).split(",")).map(Long::valueOf).collect(Collectors.toList());
    }

    public int getStartSum() {
        return Integer.parseInt(getParamValue(PARAM_NAME_START_SUM));
    }
}
