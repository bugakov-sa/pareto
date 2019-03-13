package pareto.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pareto.core.entity.Robot;

public interface RobotRepository extends JpaRepository<Robot, Long> {
}
