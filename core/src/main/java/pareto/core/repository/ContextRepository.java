package pareto.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pareto.core.entity.Context;

public interface ContextRepository extends JpaRepository<Context, Long> {
}
