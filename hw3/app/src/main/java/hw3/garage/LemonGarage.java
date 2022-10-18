package garage;

import java.util.*;
import java.util.function.*;

import car.Vehicle;
import owner.Owner;
import utils.CarComparator;
import utils.VehicleUpgrader;

public class LemonGarage<T extends Vehicle> implements Garage<T> {
    final HashMap<String, List<T>> carsOfBrand;
    final HashMap<Owner,  List<T>> carsOfOwner;

    final HashMap<Long, Owner> ownerOfId;
    final HashMap<Long, T> carOfId;

    final TreeSet<T> carsByVelocity;
    final TreeSet<T> carsByPower;

    private void registerOwner(Owner owner) {
        ownerOfId.put(owner.getOwnerId(), owner);        
    }
    
    private void registerCar(T car) {
        carOfId.put(car.getId(), car);        
    }

    private Owner findOwner(long id) {
        return ownerOfId.get(id);
    }

    private T findCar(long id) {
        return carOfId.get(id);
    }

    public LemonGarage() {
        carsOfBrand = new HashMap<String, List<T>>();
        carsOfOwner = new HashMap<Owner,  List<T>>();
        
        carOfId   = new HashMap<Long, T>();
        ownerOfId = new HashMap<Long, Owner>();

        carsByPower    = new TreeSet<T>(CarComparator.BY_POWER.create());
        carsByVelocity = new TreeSet<T>(CarComparator.BY_VELOCITY.create());
    }

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return carsOfOwner.keySet();
    }

    @Override
    public Collection<T> topThreeCarsByMaxVelocity() {
        return carsByVelocity.stream().limit(3).toList();
    }

    @Override
    public Collection<T> allCarsOfBrand(String brand) {
        return carsOfBrand.get(brand);
    }

    @Override
    public Collection<T> carsWithPowerMoreThan(int power) {
        var comparable = new Vehicle(0, "", "", 0, power, 0){};
        return carsByPower.tailSet((T) comparable);
    }

    @Override
    public Collection<T> allCarsOfOwner(Owner owner) {
        return carsOfOwner.get(owner);
    }

    @Override
    public double meanOwnersAgeOfCarBrand(String brand) {
        return allCarsOfBrand(brand).stream().mapToInt( ( T car) -> {
            return findOwner(car.getOwnerId()).getAge();
        }).average().orElse(0);
    }

    @Override
    public double meanCarNumberForEachOwner() {
        return allCarsUniqueOwners().stream().mapToInt( (Owner owner) -> {
            return carsOfOwner.get(owner).size();
        }).average().orElse(0);
    }

    @Override
    public T removeCar(long carId) {
        var car = findCar(carId);
        carOfId.remove(carId);

        carsOfBrand.get(car.getBrand()).remove(car);
        carsOfOwner.get(findOwner(car.getOwnerId())).remove(car);

        carsByVelocity.remove(car);
        carsByPower.remove(car);

        return car;
    }

    public T removeCar(T car) {
        return removeCar(car.getId());
    }

    @Override
    public void addCar(T car, Owner owner) {
        registerOwner(owner);
        registerCar(car);

        var brand = car.getBrand();
        var power = car.getPower();

        if (carsOfBrand.get(brand) == null)
        {
            carsOfBrand.put(brand, new ArrayList<T>());
        }

        if (carsOfOwner.get(owner) == null)
        {
            carsOfOwner.put(owner, new ArrayList<T>());
        }

        carsOfBrand.get(brand).add(car);
        carsOfOwner.get(owner).add(car);

        carsByVelocity.add(car);
        carsByPower.add(car);
    }

    @Override
    public void addCars(List<T> cars, Owner owner) {
        for (T car: cars) {
            addCar(car, owner);
        }
    }

    @Override
    public void removeListed(List<T> cars) {
        for (T car: cars) {
            removeCar(car);
        }
    }

    @Override
    public List<Object> upgradeWith(VehicleUpgrader upgrader) {
        return carOfId.values().stream().map(upgrader::upgrade).toList();
    }

    public List<T> filterCars(Predicate<T> predicate) {
        return carOfId.values().stream().filter(predicate).toList();
    }
}
