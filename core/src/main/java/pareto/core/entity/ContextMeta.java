package pareto.core.entity;

import java.time.LocalDateTime;
import java.util.List;

public interface ContextMeta {

    LocalDateTime getFromTime();

    LocalDateTime getToTime();

    List<String> getProducts();
}
