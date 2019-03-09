package pareto.core.entity;

public class Candle {
    private final long open, close, min, max;

    public Candle(long open, long close, long min, long max) {
        this.open = open;
        this.close = close;
        this.min = min;
        this.max = max;
    }

    public long getOpen() {
        return open;
    }

    public long getClose() {
        return close;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }
}
