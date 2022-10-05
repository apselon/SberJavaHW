package garage;

import java.util.*;

import car.Car;
import owner.Owner;
import utils.CarComparator;

public class LemonGarage implements Garage {
    final HashMap<String, List<Car>> carsOfBrand;
    final HashMap<Owner,  List<Car>> carsOfOwner;

    final HashMap<Long, Owner> ownerOfId;
    final HashMap<Long, Car> carOfId;

    final TreeSet<Car> carsByVelocity;
    final TreeSet<Car> carsByPower;

    private void registerOwner(Owner owner) {
        ownerOfId.put(owner.getOwnerId(), owner);        
    }
    
    private void registerCar(Car car) {
        carOfId.put(car.getCarId(), car);        
    }

    private Owner findOwner(long id) {
        return ownerOfId.get(id);
    }

    private Car findCar(long id) {
        return carOfId.get(id);
    }

    public LemonGarage() {
        carsOfBrand = new HashMap<String, List<Car>>();
        carsOfOwner = new HashMap<Owner,  List<Car>>();
        
        carOfId   = new HashMap<Long, Car>();
        ownerOfId = new HashMap<Long, Owner>();

        carsByPower    = new TreeSet<Car>(CarComparator.BY_POWER.create());
        carsByVelocity = new TreeSet<Car>(CarComparator.BY_VELOCITY.create());
    }

    @Override
    public Collection<Owner> allCarsUniqueOwners() {
        return carsOfOwner.keySet();
    }

    @Override
    public Collection<Car>topThreeCarsByMaxVelocity() {
        return carsByVelocity.stream().limit(3).toList();
    }

    @Override
    public Collection<Car> allCarsOfBrand(String brand) {
        return carsOfBrand.get(brand);
    }

    @Override
    public Collection<Car> carsWithPowerMoreThan(int power) {
        var comparable = new Car(power);
        return carsByPower.tailSet(comparable);
    }

    @Override
    public Collection<Car> allCarsOfOwner(Owner owner) {
        return carsOfOwner.get(owner);
    }

    @Override
    public double meanOwnersAgeOfCarBrand(String brand) {
        return allCarsOfBrand(brand).stream().mapToInt( ( Car car) -> {
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
    public Car removeCar(long carId) {
        var car = findCar(carId);
        carOfId.remove(carId);

        carsOfBrand.get(car.getBrand()).remove(car);
        carsOfOwner.get(findOwner(car.getOwnerId())).remove(car);

        carsByVelocity.remove(car);
        carsByPower.remove(car);

        return car;
    }

    @Override
    public void addCar(Car car, Owner owner) {
        registerOwner(owner);
        registerCar(car);

        var brand = car.getBrand();
        var power = car.getPower();

        if (carsOfBrand.get(brand) == null)
        {
            carsOfBrand.put(brand, new ArrayList<Car>());
        }

        if (carsOfOwner.get(owner) == null)
        {
            carsOfOwner.put(owner, new ArrayList<Car>());
        }

        carsOfBrand.get(brand).add(car);
        carsOfOwner.get(owner).add(car);

        carsByVelocity.add(car);
        carsByPower.add(car);
    }
}
