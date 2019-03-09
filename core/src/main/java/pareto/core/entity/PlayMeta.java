package pareto.core.entity;

public class PlayMeta {

    private final long id;
    private final RobotMeta robotMeta;
    private final ContextMeta contextMeta;

    public PlayMeta(long id, RobotMeta robotMeta, ContextMeta contextMeta) {
        this.id = id;
        this.robotMeta = robotMeta;
        this.contextMeta = contextMeta;
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
}
