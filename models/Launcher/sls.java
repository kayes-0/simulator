class Sls extends Launcher {

    public Sls() {
        super("SLS", true, 2, 2600, 130, 2000);
    }

    @Override
    double calculateThrust() {
        return 2500;
    }
}
