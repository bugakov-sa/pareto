package pareto.core.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import pareto.core.entity.Event;
import pareto.core.entity.EventType;
import pareto.core.entity.Param;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EventService {

    private final String schemaName;
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EventService(
            @Value("${spring.flyway.schemas}") String schemaName,
            DataSource dataSource,
            NamedParameterJdbcTemplate namedParameterJdbcTemplate
    ) {
        this.schemaName = schemaName;
        this.dataSource = dataSource;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(List<Event> events) {
        events.forEach(this::save);
    }

    private void save(Event event) {
        SimpleJdbcInsert eventInsert = new SimpleJdbcInsert(dataSource)
                .withSchemaName(schemaName)
                .withTableName("play_event")
                .usingColumns("play_id", "time", "event_type")
                .usingGeneratedKeyColumns("id");
        Map<String, ?> eventInsertParams = Map.of(
                "play_id", event.getPlayId(),
                "time", event.getTime().toEpochSecond(ZoneOffset.UTC),
                "event_type", event.getEventType().getCode()
        );
        long eventId = eventInsert.executeAndReturnKey(eventInsertParams).longValue();
        List<Param> params = event.getParams();
        Map<String, ?>[] eventParamInsertParams = new Map[params.size()];
        for (int i = 0; i < params.size(); i++) {
            eventParamInsertParams[i] = Map.of(
                    "event_id", eventId,
                    "name", params.get(i).getName(),
                    "value", params.get(i).getValue()
            );
        }
        namedParameterJdbcTemplate.batchUpdate(
                "insert into event_param(event_id, name, value) values(:event_id, :name, :value)",
                eventParamInsertParams
        );
    }

    public List<Event> getPlayEvents(long playId) {
        List<Event> res = new ArrayList<>();
        namedParameterJdbcTemplate.query(
                "select play_event.id as event_id, play_event.time as time, play_event.event_type as event_type, " +
                        "play_event.play_id as play_id, event_param.name as param_name, event_param.value as param_value " +
                        "from play_event left join event_param on play_event.id = event_param.event_id where play_event.play_id = :play_id",
                Map.of("play_id", playId),
                new RowCallbackHandler() {
                    @Override
                    public void processRow(ResultSet rs) throws SQLException {
                        if (res.isEmpty()) {
                            res.add(buildEvent(rs));
                        }
                        Event lastEvent = res.get(res.size() - 1);
                        long currEventId = rs.getLong("event_id");
                        if (currEventId != lastEvent.getId()) {
                            res.add(buildEvent(rs));
                        }
                        Event currEvent = res.get(res.size() - 1);
                        Param param = buildParam(rs);
                        if (param != null) {
                            currEvent.getParams().add(param);
                        }
                    }

                    Event buildEvent(ResultSet rs) throws SQLException {
                        Event event = new Event();
                        event.setId(rs.getLong("event_id"));
                        event.setPlayId(rs.getLong("play_id"));
                        event.setEventType(EventType.lookup(rs.getInt("event_type")));
                        event.setTime(LocalDateTime.ofEpochSecond(rs.getLong("time"), 0, ZoneOffset.UTC));
                        event.setParams(new ArrayList<>());
                        return event;
                    }

                    Param buildParam(ResultSet rs) throws SQLException {
                        if (rs.getString("param_name") == null) {
                            return null;
                        }
                        Param param = new Param();
                        param.setName(rs.getString("param_name"));
                        param.setValue(rs.getString("param_value"));
                        return param;
                    }
                }
        );
        return res;
    }
}
