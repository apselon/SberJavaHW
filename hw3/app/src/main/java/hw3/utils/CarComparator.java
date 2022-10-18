package utils;

import java.util.*;

import car.Vehicle;

public enum CarComparator {
    BY_POWER {
        @Override
        public Comparator<Vehicle> create() {
            return new PowerComparator();
        }

    },
    BY_VELOCITY {
        @Override
        public Comparator<Vehicle> create() {
            return new VelocityComparator();
        }

    };

    public abstract Comparator<Vehicle> create();
}

class PowerComparator implements Comparator<Vehicle> {
    public int compare(Vehicle a, Vehicle b) {
        if (a.getId() == b.getId()) {
            return 0;
        }

        var power_diff = Integer.compare(a.getPower(), b.getPower());
        return (power_diff == 0)?(1):(power_diff);
    }
}

class VelocityComparator implements Comparator<Vehicle> {
    public int compare(Vehicle a, Vehicle b) {
        if (a.getId() == b.getId()) {
            return 0;
        }

        var power_diff = Integer.compare(b.getMaxVelocity(), a.getMaxVelocity());
        return (power_diff == 0)?(-1):(power_diff);
    }
}
