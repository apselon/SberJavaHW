package car;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Car {
    public Car(int power) {
        this.carId = 0;
        this.brand = "";
        this.modelName = "";
        this.maxVelocity = 0;
        this.power = power;
        this.ownerId = 0;
    }

    private final long carId;
    private final String brand;
    private final String modelName;
    private final int maxVelocity;
    private final int power;
    private final long ownerId;
}
