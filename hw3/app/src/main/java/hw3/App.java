package hw3;

import java.util.*;

import garage.LemonGarage;
import car.Car;
import owner.Owner;


public class App {
    public static void main(String[] args) {
        var garage = new LemonGarage<Car>();

        var lenin_owner = new Owner(1, "Vladimir", "Lenin", 47);

        var lenins_cars = Arrays.asList(new Car[] {
            new Car(1, "Delaunay-Belleville", "I", 30, 10, 1),
            new Car(2, "Delaunay-Belleville", "II", 40, 15, 1),
            new Car(2, "Rolls-Royce", "Silver Ghost", 50, 10, 1),
        });

        lenins_cars.forEach((Car car) -> garage.addCar(car, lenin_owner));
        var brand_cars = garage.allCarsOfBrand("Delaunay-Belleville");

        assert(brand_cars.stream().anyMatch(car -> car.getBrand().equals("Delaunay-Belleville")));
    }
}
