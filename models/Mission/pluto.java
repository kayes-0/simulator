class Pluto extends Mission {
    public Pluto() {
        super("Pluto", true, 480000000, 0.00067);
    }

    @Override
    double calculateFuel(double weight) {
        return (weight * distance * coefficient) / 1000;
    }
}