package pareto.core.repository;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlayMetaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PlayMetaRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
