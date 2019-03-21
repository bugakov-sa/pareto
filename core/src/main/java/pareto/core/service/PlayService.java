package pareto.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import pareto.core.entity.Play;
import pareto.core.entity.PlayStatus;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PlayService {

    private final String schemaName;
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PlayService(
            @Value("${spring.flyway.schemas}") String schemaName,
            DataSource dataSource,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.schemaName = schemaName;
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Play createPlay(long robotId, long contextId) {
        SimpleJdbcInsert playInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName(schemaName)
                .withTableName("play")
                .usingColumns("robot_id", "context_id", "status")
                .usingGeneratedKeyColumns("id");
        Map<String, ?> playInsertParams = Map.of(
                "robot_id", robotId,
                "context_id", contextId,
                "status", PlayStatus.CREATED.getCode()
        );
        long playId = playInsert.executeAndReturnKey(playInsertParams).longValue();
        return getPlay(playId).get();
    }

    public void updatePlayStatus(long id, PlayStatus status) {
        namedParameterJdbcTemplate.update(
                "update play set status = :status where id = :id",
                Map.of("status", status.getCode(), "id", id)
        );
    }

    public List<Play> getAllPlays() {
        return namedParameterJdbcTemplate.query("select * from play order by id", getRowMapper());
    }

    public List<Play> getNewPlays() {
        return namedParameterJdbcTemplate.query("select * from play where status = 0 order by id", getRowMapper());
    }

    public Optional<Play> getPlay(long id) {
        return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                "select * from play where id = :id", Map.of("id", id), getRowMapper()));
    }

    private RowMapper<Play> getRowMapper() {
        return (rs, rowNum) -> {
            Play play = new Play();
            play.setId(rs.getLong("id"));
            play.setRobotId(rs.getLong("robot_id"));
            play.setContextId(rs.getLong("context_id"));
            play.setStatus(PlayStatus.lookup(rs.getInt("status")));
            return play;
        };
    }
}
