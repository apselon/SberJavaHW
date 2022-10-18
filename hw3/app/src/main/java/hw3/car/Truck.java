package car;

public class Truck extends Vehicle {
    public Truck(
            long id,
            String brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId,
            Double wheelDiameter)
    {
        super(id, brand, modelName, maxVelocity, power, ownerId);
        this.wheelDiameter = wheelDiameter;
    }

    public Double getWheelDiameter() {
        return wheelDiameter;
    }

    private final Double wheelDiameter; 
}
