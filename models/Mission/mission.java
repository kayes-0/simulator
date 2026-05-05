abstract class Mission {
    String name;
    boolean crewed;
    double distance;
    double coefficient;

    public Mission(String name, boolean crewed, double distance, double coefficient) {
        this.name = name;
        this.crewed = crewed;
        this.distance = distance;
        this.coefficient = coefficient;
    }

    abstract double calculateFuel(double mass);

    public String getName() {
        return name;
    }

    public boolean isCrewedRequired() {
        return crewed;
    }

    public double calculateRequiredFuel(Rocket rocket) {
        return calculateFuel(rocket.calculateTotalMass());
    }

    @Override
    public String toString() {
        return name + " | crewed required: " + crewed
                + " | distance: " + distance + " km"
                + " | fuel coefficient: " + coefficient;
    }
}
