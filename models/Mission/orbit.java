class Orbit extends Mission {
    public Orbit() {
        super("Orbit", false, 400, 1.0);
    }

    @Override
    double calculateFuel(double weight) {
        return (weight * distance * coefficient) / 1000;
    }
}