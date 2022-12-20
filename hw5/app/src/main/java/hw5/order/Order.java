package order;

public class Order {

    private final long orderId;
    public static long stopId = -1;

    public Order(long orderId) {
        this.orderId = orderId;
    }

    public long getId() {
        return orderId;
    }

    public void execute() {
        try {
            Thread.sleep(100);
            System.out.println(this.orderId + " completed!");
        }

        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
