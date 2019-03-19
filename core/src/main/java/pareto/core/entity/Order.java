package pareto.core.entity;

public class Order {

    private final long productId;
    private final PositionType positionType;
    private final int positionSize;

    private Order(long productId, PositionType positionType, int positionSize) {
        this.productId = productId;
        this.positionType = positionType;
        this.positionSize = positionSize;
    }

    public static Order createLong(long productId, int positionSize) {
        return new Order(productId, PositionType.LONG, positionSize);
    }

    public static Order createLong(long productId) {
        return new Order(productId, PositionType.LONG, 1);
    }

    public static Order createShort(long productId, int positionSize) {
        return new Order(productId, PositionType.SHORT, positionSize);
    }

    public static Order createShort(long productId) {
        return new Order(productId, PositionType.SHORT, 1);
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
}
