package pareto.core.entity;

public class PlayMeta {

    private final long id;
    private final RobotMeta robotMeta;
    private final ContextMeta contextMeta;
    private final PlayStatus status;

    public PlayMeta(long id, RobotMeta robotMeta, ContextMeta contextMeta, PlayStatus status) {
        this.id = id;
        this.robotMeta = robotMeta;
        this.contextMeta = contextMeta;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public RobotMeta getRobotMeta() {
        return robotMeta;
    }

    public ContextMeta getContextMeta() {
        return contextMeta;
    }

    public PlayStatus getStatus() {
        return status;
    }
}
