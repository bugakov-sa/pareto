package pareto.core.entity;

import java.time.LocalDateTime;

public class Order {

    public static final String PARAM_PRODUCT = "product";
    public static final String PARAM_POSITION_TYPE = "positionType";
    public static final String PARAM_POSITION_SIZE = "positionSize";
    public static final String PARAM_POSITION_OPEN_PRICE = "positionOpenPrice";

    private final long productId;
    private final PositionType positionType;
    private final int positionSize;
    private final LocalDateTime time;

    private Order(long productId, PositionType positionType, int positionSize, LocalDateTime time) {
        this.productId = productId;
        this.positionType = positionType;
        this.positionSize = positionSize;
        this.time = time;
    }

    public static Order createLong(long productId, int positionSize, LocalDateTime time) {
        return new Order(productId, PositionType.LONG, positionSize, time);
    }

    public static Order createLong(long productId, LocalDateTime time) {
        return new Order(productId, PositionType.LONG, 1, time);
    }

    public static Order createShort(long productId, int positionSize, LocalDateTime time) {
        return new Order(productId, PositionType.SHORT, positionSize, time);
    }

    public static Order createShort(long productId, LocalDateTime time) {
        return new Order(productId, PositionType.SHORT, 1, time);
    }

    public long getProductId() {
        return productId;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public int getPositionSize() {
        return positionSize;
    }

    public LocalDateTime getTime() {
        return time;
    }
}
