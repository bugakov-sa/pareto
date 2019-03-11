package pareto.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pareto.core.entity.Algorithm;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
}
