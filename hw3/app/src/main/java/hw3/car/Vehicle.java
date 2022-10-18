package car;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class Vehicle {
    public Vehicle(int power) {
        this.id = 0;
        this.brand = "";
        this.modelName = "";
        this.maxVelocity = 0;
        this.power = power;
        this.ownerId = 0;
    }

    private final long id;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final long ownerId;
}
