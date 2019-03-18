package pareto.core.entity;

public class Order {

    private final long productId;

    public Order(long productId) {
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}
