package garage;

import java.util.*;
import java.util.function.*;

import car.Car;
import owner.Owner;
import utils.VehicleUpgrader;

public interface Garage<T> {

    Collection<Owner> allCarsUniqueOwners();

    // Complexity should be less than O(n)
    Collection<T> topThreeCarsByMaxVelocity();

    // Complexity should be O(1)
    Collection<T> allCarsOfBrand(String brand);

    // Complexity should be less than O(n)
    Collection<T> carsWithPowerMoreThan(int power);

    /**
     * Complexity should be O(1)
     */
    Collection<T> allCarsOfOwner(Owner owner);

    /**
     * @return mean value of owner age that has cars with given brand
     */
    double meanOwnersAgeOfCarBrand(String brand);

    /**
     * @return mean value of cars for all owners
     */
    double meanCarNumberForEachOwner();

    /**
     * Complexity should be less than O(n)
     * @return removed car
     */
    T removeCar(long carId);

    /**
     * Complexity should be less than O(n)
     */
    void addCar(T car, Owner owner);

    void addCars(List<T> cars, Owner owner);

    T removeCar(T car);

    void removeListed(List<T> cars);

    List<Object> upgradeWith(VehicleUpgrader upgrader);

    List<T> filterCars(Predicate<T> predicate);
}
