package pareto.core.entity;

import java.time.LocalDateTime;

public class Quotation {

    private long productId;
    private LocalDateTime time;
    private int open;
    private int close;
    private int min;
    private int max;

    public Quotation() {
    }

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

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getClose() {
        return close;
    }

    public void setClose(int close) {
        this.close = close;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
