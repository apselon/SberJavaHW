import java.util.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import garage.LemonGarage;
import car.Car;
import owner.Owner;

public class LemonGarageTest {
    @Test
    public void allCarsOfBrandTest() {
        var garage = new LemonGarage();

        var lenin_owner = new Owner(1, "Vladimir", "Lenin", 47);

        var lenins_cars = Arrays.asList(new Car[] {
            new Car(1, "Delaunay-Belleville", "I", 30, 10, 1),
            new Car(2, "Delaunay-Belleville", "II", 40, 15, 1),
            new Car(2, "Rolls-Royce", "Silver Ghost", 50, 10, 1),
        });

        lenins_cars.forEach((Car car) -> garage.addCar(car, lenin_owner));
        var brand_cars = garage.allCarsOfBrand("Delaunay-Belleville");

        Assertions.assertTrue(brand_cars.stream().anyMatch(car -> car.getBrand().equals("Delaunay-Belleville")));
    }

    @Test
    public void allCarsOfOwnerTest() {
        var garage = new LemonGarage();

        var lenin_owner = new Owner(1, "Vladimir", "Lenin", 47);

        var lenins_cars = Arrays.asList(new Car[] {
            new Car(1, "Delaunay-Belleville", "I", 30, 10, 1),
            new Car(2, "Delaunay-Belleville", "II", 40, 15, 1),
            new Car(2, "Rolls-Royce", "Silver Ghost", 50, 10, 1),
        });

        lenins_cars.forEach((Car car) -> garage.addCar(car, lenin_owner));
        var owner_cars = garage.allCarsOfOwner(lenin_owner);

        Assertions.assertTrue(owner_cars.equals(lenins_cars));
    }

    @Test
    public void allCarsUniqueOwners() {
        var owners = Arrays.asList(new Owner[] {
            new Owner(1, "Vladimir", "Lenin", 47),
            new Owner(2, "Don", "Yago", 35),
            new Owner(3, "Valeriya", "Novodvorskaya", 66),
            new Owner(4, "Billy", "Herrington", 76),
            new Owner(5, "Michael", "Smirnoff", 21),
            new Owner(6, "Dmitry", "Startsev", 21),
            new Owner(7, "Nikolay", "Sinebrychoff", 44)
        });

        var garage = new LemonGarage();

        for (int i = 0; i < 3; ++i) {
            for (int j = 1; j <= 7; ++j) {
                var car = new Car(j + i * 7, "Lada", "Priora", 200, 100, j);
                garage.addCar(car, owners.get(j - 1));
            }
        }

        var owner_id_comparator = Comparator.comparingLong(Owner::getOwnerId);
        var unique_owners = garage.allCarsUniqueOwners().stream().sorted(owner_id_comparator).toList();

        Assertions.assertTrue(unique_owners.equals(owners));
    }

    @Test
    public void carsWithPowerMoreThanTest() {
        var garage = new LemonGarage();

        var lenin_owner = new Owner(1, "Vladimir", "Lenin", 47);

        var lenins_cars = Arrays.asList(new Car[] {
            new Car(1, "Delaunay-Belleville", "I", 30, 10, 1),
            new Car(2, "Delaunay-Belleville", "II", 40, 15, 1),
            new Car(2, "Rolls-Royce", "Silver Ghost", 50, 10, 1),
        });

        lenins_cars.forEach((Car car) -> garage.addCar(car, lenin_owner));

        var power_cars = garage.carsWithPowerMoreThan(12);
        Assertions.assertTrue(power_cars.stream().anyMatch(car -> car.getPower() > 12));
    }

    @Test
    public void topThreeCarsByMaxVelocityTest() {
        var garage = new LemonGarage();

        var lenin_owner = new Owner(1, "Vladimir", "Lenin", 47);

        var lenins_cars = Arrays.asList(new Car[] {
            new Car(1, "Delaunay-Belleville", "I", 30, 10, 1),
            new Car(2, "Delaunay-Belleville", "II", 40, 15, 1),
            new Car(3, "Rolls-Royce", "Silver Ghost", 50, 10, 1),
            new Car(4, "Rolls-Royce", "Gray Goose", 50, 10, 1),
            new Car(5, "Rolls-Royce", "Red Dragon", 50, 10, 1),
        });

        lenins_cars.forEach((Car car) -> garage.addCar(car, lenin_owner));

        var speed_cars = garage.topThreeCarsByMaxVelocity();
        Assertions.assertTrue(speed_cars.stream().anyMatch(car -> car.getMaxVelocity() == 50));
    }

    @Test
    public void meanCarNumberForEachOwnerTest() {
        var owners = Arrays.asList(new Owner[] {
            new Owner(1, "Vladimir", "Lenin", 47),
            new Owner(2, "Don", "Yago", 35),
            new Owner(3, "Valeriya", "Novodvorskaya", 66),
            new Owner(4, "Billy", "Herrington", 76),
            new Owner(5, "Michael", "Smirnoff", 21),
            new Owner(6, "Dmitry", "Startsev", 21),
            new Owner(7, "Nikolay", "Sinebrychoff", 44)
        });

        var garage = new LemonGarage();

        for (int i = 0; i < 3; ++i) {
            for (int j = 1; j <= 7; ++j) {
                var car = new Car(j + i * 7, "Lada", "Priora", 200, 100, j);
                garage.addCar(car, owners.get(j - 1));
            }
        }

        Assertions.assertEquals(garage.meanCarNumberForEachOwner(), 3);
    }

    @Test
    public void meanOwnersAgeOfCarBrandTest() {
        var owners = Arrays.asList(new Owner[] {
            new Owner(1, "Vladimir", "Lenin", 47),
            new Owner(2, "Don", "Yago", 35),
            new Owner(3, "Valeriya", "Novodvorskaya", 66),
            new Owner(4, "Billy", "Herrington", 76),
            new Owner(5, "Michael", "Smirnoff", 21),
            new Owner(6, "Dmitry", "Startsev", 21),
            new Owner(7, "Nikolay", "Sinebrychoff", 44)
        });

        var garage = new LemonGarage();

        for (int i = 0; i < 3; ++i) {
            for (int j = 1; j <= 7; ++j) {
                var car = new Car(j + i * 7, "Lada", "Priora", 200, 100, j);
                garage.addCar(car, owners.get(j - 1));
            }
        }

        Assertions.assertEquals(garage.meanOwnersAgeOfCarBrand("Lada"), 44.285714, 0.001);
    }
}
