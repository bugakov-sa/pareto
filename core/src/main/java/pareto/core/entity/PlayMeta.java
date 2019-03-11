package pareto.core.entity;

public class PlayMeta {

    private final long id;
    private final Robot robot;
    private final Context context;
    private final PlayStatus status;

    public PlayMeta(long id, Robot robot, Context context, PlayStatus status) {
        this.id = id;
        this.robot = robot;
        this.context = context;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public Robot getRobot() {
        return robot;
    }

    public Context getContext() {
        return context;
    }

    public PlayStatus getStatus() {
        return status;
    }
}
