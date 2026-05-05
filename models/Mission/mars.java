class Mars extends Mission {

    public Mars() {
        super("Mars", true, 225000000, 0.000015);
    }

    @Override
    double calculateFuel(double weight) {
        return (weight * distance * coefficient) / 1000;
    }
}