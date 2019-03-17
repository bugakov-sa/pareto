package pareto.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import pareto.core.entity.Context;
import pareto.core.entity.Param;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ContextService {

    private static final String INSERT_CONTEXT_PARAM = "insert into context_param(context_id, name, value) values(:context_id, :name, :value)";
    private static final String SELECT_ALL_CONTEXTS = "select context.id as context_id, context.name as context_name, context.description as context_description, context_param.name as param_name, context_param.value as param_value from context join context_param on context.id=context_param.context_id order by context.id";
    private static final String SELECT_CONTEXT_BY_ID = "select context.id as context_id, context.name as context_name, context.description as context_description, context_param.name as param_name, context_param.value as param_value from context join context_param on context.id=context_param.context_id where context.id = :context_id order by context.id";

    private final String schemaName;
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ContextService(
            @Value("${spring.flyway.schemas}") String schemaName,
            DataSource dataSource,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.schemaName = schemaName;
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Context createContext(String name, String description, List<Param> params) {

        SimpleJdbcInsert contextInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName(schemaName)
                .withTableName("context")
                .usingColumns("name", "description")
                .usingGeneratedKeyColumns("id");
        Map<String, String> contextInsertParams = Map.of(
                "name", name,
                "description", description
        );
        long contextId = contextInsert.executeAndReturnKey(contextInsertParams).longValue();

        Map<String, ?>[] contextParamInsertParams = new Map[params.size()];
        for (int i = 0; i < params.size(); i++) {
            contextParamInsertParams[i] = Map.of(
                    "context_id", contextId,
                    "name", params.get(i).getName(),
                    "value", params.get(i).getValue()
            );
        }
        namedParameterJdbcTemplate.batchUpdate(INSERT_CONTEXT_PARAM, contextParamInsertParams);

        return getContext(contextId).get();
    }


    public List<Context> getAllContexts() {
        List<Context> contexts = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_ALL_CONTEXTS, getRowCallbackHandler(contexts));
        return contexts;
    }

    private RowCallbackHandler getRowCallbackHandler(List<Context> contexts) {
        return new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                Context currContext = buildContext(rs);
                if (contexts.isEmpty()) {
                    contexts.add(currContext);
                } else {
                    Context lastContext = contexts.get(contexts.size() - 1);
                    if (!currContext.getId().equals(lastContext.getId())) {
                        contexts.add(currContext);
                    }
                }
                currContext = contexts.get(contexts.size() - 1);
                currContext.getParams().add(buildParam(rs));
            }

            private Context buildContext(ResultSet rs) throws SQLException {
                Context context = new Context();
                context.setId(rs.getLong("context_id"));
                context.setName(rs.getString("context_name"));
                context.setDescription(rs.getString("context_description"));
                context.setParams(new ArrayList<>());
                return context;
            }

            private Param buildParam(ResultSet rs) throws SQLException {
                Param param = new Param();
                param.setName(rs.getString("param_name"));
                param.setValue(rs.getString("param_value"));
                return param;
            }
        };
    }

    public Optional<Context> getContext(long id) {
        List<Context> contexts = new ArrayList<>();
        namedParameterJdbcTemplate.query(SELECT_CONTEXT_BY_ID, Map.of("context_id", id), getRowCallbackHandler(contexts));
        return contexts.isEmpty() ? Optional.empty() : Optional.of(contexts.get(0));
    }
}
