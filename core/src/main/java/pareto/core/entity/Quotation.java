package pareto.core.entity;

import java.time.LocalDateTime;

public class Quotation {

    private final long productId;
    private final LocalDateTime time;
    private final int open;
    private final int close;
    private final int min;
    private final int max;

    public Quotation(long productId, LocalDateTime time, int open, int close, int min, int max) {
        this.productId = productId;
        this.time = time;
        this.open = open;
        this.close = close;
        this.min = min;
        this.max = max;
    }

    public long getProductId() {
        return productId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getOpen() {
        return open;
    }

    public int getClose() {
        return close;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
