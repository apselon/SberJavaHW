package taxi;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import dispatcher.Dispatcher;
import order.Order;
import order.SyncedOrder;

public class CityMobilTaxi implements Taxi {
  private final List<Order> executedOrders;
  private final Thread worker;
  private final Dispatcher dispatcher;
  private final SyncedOrder curOrder;

  public CityMobilTaxi(Dispatcher dispatcher) {
    this.executedOrders = new ArrayList<Order>();
    this.dispatcher = dispatcher;
    this.curOrder = new SyncedOrder();

    this.worker = new Thread(() -> {
      try {
        while (true) {
          synchronized(curOrder) {
            while (!curOrder.isSet()) {
              System.out.println("Taxi started waiting for order");
              curOrder.wait();
            }
            
            System.out.println("Taxi got order");
            if (curOrder.getOrder().getId() == Order.stopId) {
              System.out.println("Taxi stops");
              break;
            }

            curOrder.getOrder().execute();
            executedOrders.add(curOrder.getOrder());
            curOrder.reset();

            dispatcher.notifyAvailable(this);
          }
        }
      }

      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public void run() {
    worker.start();   
  }

  @Override
  public void stop() {
    placeOrder(new Order(Order.stopId));
    try {
      worker.join();
    }
    
    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void placeOrder(Order order) {
    synchronized (curOrder) {
      curOrder.setOrder(order);
      curOrder.notify();
    }
  }

  @Override
  public List<Order> getExecutedOrders() {
    synchronized (executedOrders) {
      return executedOrders;
    }
  }
}
