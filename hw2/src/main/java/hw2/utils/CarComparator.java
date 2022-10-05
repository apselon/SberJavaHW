package utils;

import java.util.*;

import car.Car;

public enum CarComparator {
    BY_POWER {
        @Override
        public Comparator<Car> create() {
            return new PowerComparator();
        }

    },
    BY_VELOCITY {
        @Override
        public Comparator<Car> create() {
            return new VelocityComparator();
        }

    };

    public abstract Comparator<Car> create();
}

class PowerComparator implements Comparator<Car> {
    public int compare(Car a, Car b) {
        if (a.getCarId() == b.getCarId()) {
            return 0;
        }

        var power_diff = Integer.compare(a.getPower(), b.getPower());
        return (power_diff == 0)?(1):(power_diff);
    }
}

class VelocityComparator implements Comparator<Car> {
    public int compare(Car a, Car b) {
        if (a.getCarId() == b.getCarId()) {
            return 0;
        }

        var power_diff = Integer.compare(b.getMaxVelocity(), a.getMaxVelocity());
        return (power_diff == 0)?(-1):(power_diff);
    }
}
