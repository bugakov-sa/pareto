package pareto.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pareto.core.entity.Play;

public interface PlayRepository extends JpaRepository<Play, Long> {
}
