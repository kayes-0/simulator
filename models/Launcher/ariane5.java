class Ariane5 extends Launcher {

    public Ariane5() {
        super("Ariane 5", false, 2, 700, 20, 180);
    }

    @Override
    double calculateThrust() {
        return 2500;
    }
}
