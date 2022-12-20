package order;

public class SyncedOrder {
    private Order order;

    public SyncedOrder() {
        this.order = null;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return this.order;
    }

    public void reset() {
        this.order = null;
    }

    public boolean isSet() {
        return this.order != null;
    }
}
