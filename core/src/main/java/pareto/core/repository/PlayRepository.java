package pareto.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pareto.core.entity.Play;

import java.util.List;

public interface PlayRepository extends JpaRepository<Play, Long> {

    List<Play> findByStatus(int status);
}
