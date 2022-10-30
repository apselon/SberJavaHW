package hw4;

import java.util.*;
import java.io.*;

import garage.LemonGarage;
import car.Car;
import owner.Owner;
import report.MsExcelReportGenerator;


public class App {
    public static void main(String[] args) throws IOException {
        var garage = new LemonGarage();

        var lenin_owner = new Owner(1, "Vladimir", "Lenin", 47);

        var lenins_cars = Arrays.asList(new Car[] {
            new Car(1, "Delaunay-Belleville", "I", 30, 10, 1),
            new Car(2, "Delaunay-Belleville", "II", 40, 15, 1),
            new Car(2, "Rolls-Royce", "Silver Ghost", 50, 10, 1),
        });

        lenins_cars.forEach((Car car) -> garage.addCar(car, lenin_owner));
        var brand_cars = garage.allCarsOfBrand("Delaunay-Belleville");

        var reportGenerator = new MsExcelReportGenerator<Car>();
        var report = reportGenerator.generate(brand_cars.stream().toList(), "Delaunay-Belleville car");

        report.writeTo(new FileOutputStream("CarsOfBrand.xls"));
    }
}
