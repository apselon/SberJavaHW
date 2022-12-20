package dispatcher;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import taxi.Taxi;
import taxi.CityMobilTaxi;
import order.Order;

public class CityMobilDispatcher implements Dispatcher {

  private long orderCounter;
  private final BlockingQueue<Order> orders;

  private long numTaxis;
  private final BlockingQueue<Taxi> freeDrivers;

  public CityMobilDispatcher(long numTaxis) {
    this.orderCounter = 0;
    this.numTaxis = numTaxis;

    this.orders      = new LinkedBlockingQueue<Order>();
    this.freeDrivers = new LinkedBlockingQueue<Taxi>();

    for (long i = 0; i < numTaxis; ++i) {
      var curTaxi = new CityMobilTaxi(this);
      freeDrivers.add(curTaxi);
      curTaxi.run();
    }
  }

  public void addOrders(long numOrders) {
    for (long i = 0; i < numOrders; ++i) {
      orders.add(new Order(nextOrderId()));
    }

    orders.add(new Order(Order.stopId));
  }

  @Override
  public void notifyAvailable(Taxi taxi) {
    synchronized (freeDrivers) {
      freeDrivers.add(taxi);
    }
  }

  @Override
  public void placeOrder(Taxi taxi, Order order) {
    taxi.placeOrder(order);
  }

  @Override
  public void run() {
    try {
      while (true) {
        var curOrder = orders.take();

        if (curOrder.getId() == Order.stopId) {
          System.out.println("Started sending stop to taxis");
          stop();
          break;
        }

        var curTaxi = freeDrivers.take();
        placeOrder(curTaxi, curOrder);
      }
    }

    catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void stop() throws InterruptedException {
    while (numTaxis > 0) {
        var curTaxi = freeDrivers.take();
        curTaxi.stop();
        --numTaxis;
    }
  }

  private long nextOrderId() {
    return orderCounter++;
  }
}
