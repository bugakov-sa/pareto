package pareto.core.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PlayStatusId implements Serializable {

    private long playId;
    private LocalDateTime time;

    public long getPlayId() {
        return playId;
    }

    public void setPlayId(long playId) {
        this.playId = playId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
