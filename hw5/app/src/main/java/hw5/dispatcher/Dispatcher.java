package dispatcher;

import taxi.Taxi;
import order.Order;

public interface Dispatcher {
  void notifyAvailable(Taxi taxi);

  void placeOrder(Taxi taxi, Order order);

  void run();
}
