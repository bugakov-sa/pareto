package pareto.core.entity;

public enum  EventType {
    NEW_ORDER(0),
    ORDER_EXECUTION(1);

    private final int code;

    EventType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EventType lookup(int code) {
        for(EventType eventType : values()) {
            if(eventType.getCode() == code) {
                return eventType;
            }
        }
        return null;
    }
}
