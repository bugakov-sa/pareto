package pareto.core.entity;

public enum PlayStatus {
    CREATED(0),
    STARTED(1),
    FINISHED(2);

    private final int code;

    PlayStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PlayStatus lookup(int code) {
        for(PlayStatus playStatus : values()) {
            if(playStatus.getCode() == code) {
                return playStatus;
            }
        }
        return null;
    }
}
