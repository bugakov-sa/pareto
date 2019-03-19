package pareto.core.entity;

public class Position {

    private final long productId;
    private final PositionType type;
    private final int size;
    private final int openPrice;

    public Position(long productId, PositionType type, int size, int openPrice) {
        this.productId = productId;
        this.type = type;
        this.size = size;
        this.openPrice = openPrice;
    }

    public long getProductId() {
        return productId;
    }

    public PositionType getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public int getOpenPrice() {
        return openPrice;
    }
}
