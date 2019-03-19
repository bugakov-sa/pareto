package pareto.core.service;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import pareto.core.entity.PlayPnl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Service
public class PlayPnlService {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PlayPnlService(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public void save(PlayPnl playPnl) {
        namedParameterJdbcTemplate.update(
                "insert into play_pnl(play_id, time, open, close, min, max) values(:play_id, :time, :open, :close, :min, :max)",
                Map.of(
                        "play_id", playPnl.getPlayId(),
                        "time", playPnl.getTime().toEpochSecond(ZoneOffset.UTC),
                        "open", playPnl.getOpen(),
                        "close", playPnl.getClose(),
                        "min", playPnl.getMin(),
                        "max", playPnl.getMax()
                )
        );
    }

    public List<PlayPnl> getPlayPnl(long playId) {
        return namedParameterJdbcTemplate.query(
                "select * from play_pnl where play_id = :play_id",
                Map.of("play_id", playId),
                (rs, rowNum) -> {
                    PlayPnl res = new PlayPnl();
                    res.setPlayId(playId);
                    res.setTime(LocalDateTime.ofEpochSecond(rs.getLong("time"), 0, ZoneOffset.UTC));
                    res.setOpen(rs.getInt("open"));
                    res.setClose(rs.getInt("close"));
                    res.setMin(rs.getInt("min"));
                    res.setMax(rs.getInt("max"));
                    return res;
                });
    }
}
