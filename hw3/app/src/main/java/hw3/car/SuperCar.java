package car;

public class SuperCar extends Vehicle {
    public SuperCar(
            long id,
            String brand,
            String modelName,
            int maxVelocity,
            int power,
            long ownerId,
            String racingTeam) 
    {
        super(id, brand, modelName, maxVelocity, power, ownerId);
        this.racingTeam = racingTeam;
    }

    public String getRacingTeam() {
        return racingTeam;
    }

    private final String racingTeam;
}
