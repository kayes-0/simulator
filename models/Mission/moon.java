class Moon extends Mission {
    public Moon() {
        super("Moon", true, 400000, 0.005);
    }

    @Override
    double calculateFuel(double weight) {
        return (weight * distance * coefficient) / 1000;
    }
}