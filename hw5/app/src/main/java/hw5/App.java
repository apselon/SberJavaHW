package hw5;

import taxi.CityMobilTaxi;
import dispatcher.CityMobilDispatcher;
import order.Order;

public class App {
    public static void main(String[] args) {
        var d = new CityMobilDispatcher(10);
        d.addOrders(17);
        d.run();
    }
}
