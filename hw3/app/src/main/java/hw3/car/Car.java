package car;

import lombok.Getter;

public class Car extends Vehicle {
    public Car(
            long id,
            String brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId)
    {
        super(id, brand, modelName, maxVelocity, power, ownerId);
    }
}
