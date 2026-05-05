class Iss extends Mission {
    public Iss() {
        super("ISS", true, 400, 1.2);
    }

    @Override
    double calculateFuel(double weight) {
        return (weight * distance * coefficient) / 1000;
    }
}