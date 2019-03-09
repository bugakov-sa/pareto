package pareto.core.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RobotMetaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public RobotMetaRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


}
