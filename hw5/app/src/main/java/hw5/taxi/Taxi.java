package taxi;

import java.util.List;

import order.Order;

public interface Taxi {
  void run();

  void placeOrder(Order order);

  List<Order> getExecutedOrders();

  void stop();
}
