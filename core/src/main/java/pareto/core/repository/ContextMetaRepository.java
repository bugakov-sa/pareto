package pareto.core.repository;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import pareto.core.entity.ContextMeta;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ContextMetaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public ContextMetaRepository(NamedParameterJdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public ContextMeta getContextMeta(long id) {
        List<ContextMeta> res = new ArrayList<>();
        jdbcTemplate.query(
                "select * from core.context c join core.context_param p on c.id=p.context_id where c.id=:id",
                Map.of("id", id),
                new ContextMetaRowCallbackHandler(res)
        );
        return res.isEmpty() ? null : res.get(0);
    }

    public List<ContextMeta> getContextMetas() {
        List<ContextMeta> res = new ArrayList<>();
        jdbcTemplate.query(
                "select * from core.context c join core.context_param p on c.id=p.context_id order by c.id",
                new ContextMetaRowCallbackHandler(res)
        );
        return res;
    }

    public long saveNewContextMeta(ContextMeta contextMeta) {
        long id = new SimpleJdbcInsert(dataSource)
                .withTableName("core.context")
                .usingColumns("name", "description")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(Map.of(
                        "name", contextMeta.getName(),
                        "description", contextMeta.getDescription()
                )).longValue();
        List<MapSqlParameterSource> params = contextMeta.getParams().entrySet().stream()
                .map(entry -> new MapSqlParameterSource("context_id", id)
                        .addValue("name", entry.getKey())
                        .addValue("value", entry.getValue())
                ).collect(Collectors.toList());
        MapSqlParameterSource[] paramsArray = new MapSqlParameterSource[params.size()];
        paramsArray = params.toArray(paramsArray);
        jdbcTemplate.batchUpdate(
                "insert into core.context_param(context_id, name, value) values(:context_id, :name, :value)",
                paramsArray
        );
        return id;
    }

    class ContextMetaRowCallbackHandler implements RowCallbackHandler {

        private final List<ContextMeta> res;

        public ContextMetaRowCallbackHandler(List<ContextMeta> res) {
            this.res = res;
        }

        @Override
        public void processRow(ResultSet rs) throws SQLException {
            ContextMeta lastContextMeta = res.isEmpty() ? null : res.get(res.size() - 1);
            if (lastContextMeta == null || lastContextMeta.getId() != rs.getLong("id")) {
                res.add(new ContextMeta(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }
            ContextMeta currContextMeta = res.get(res.size() - 1);
            currContextMeta = currContextMeta.addParam(
                    rs.getString("name"),
                    rs.getString("value")
            );
            res.set(res.size() - 1, currContextMeta);
        }
    }
}
