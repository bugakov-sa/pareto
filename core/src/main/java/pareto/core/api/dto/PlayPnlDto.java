package pareto.core.api.dto;

import java.time.LocalDateTime;

public class PlayPnlDto {

    private LocalDateTime time;
    private int open;
    private int close;
    private int min;
    private int max;

    public PlayPnlDto() {
    }

    public PlayPnlDto(LocalDateTime time, int open, int close, int min, int max) {
        this.time = time;
        this.open = open;
        this.close = close;
        this.min = min;
        this.max = max;
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
