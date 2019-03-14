package pareto.core.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "play")
public class Play {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "robot_id")
    private Robot robot;
    @ManyToOne
    @JoinColumn(name = "context_id")
    private Context context;
    @OneToMany(mappedBy = "playId", cascade = CascadeType.ALL)
    private List<PlayStatus> status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Robot getRobot() {
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<PlayStatus> getStatus() {
        return status;
    }

    public void setStatus(List<PlayStatus> status) {
        this.status = status;
    }
}
