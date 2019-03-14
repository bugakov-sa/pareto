package pareto.core.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "play_status")
@IdClass(PlayStatusId.class)
public class PlayStatus {

    @Id
    @Column(name = "play_id")
    private long playId;
    @Id
    private LocalDateTime time;
    @Column(name = "status")
    private int statusCode;

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

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
