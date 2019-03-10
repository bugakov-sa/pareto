package pareto.core.entity;

public class PlayMeta {

    private final long id;
    private final RobotMeta robotMeta;
    private final Context context;
    private final PlayStatus status;

    public PlayMeta(long id, RobotMeta robotMeta, Context context, PlayStatus status) {
        this.id = id;
        this.robotMeta = robotMeta;
        this.context = context;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public RobotMeta getRobotMeta() {
        return robotMeta;
    }

    public Context getContext() {
        return context;
    }

    public PlayStatus getStatus() {
        return status;
    }
}
