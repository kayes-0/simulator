class Falcon9 extends Launcher {
    
    public Falcon9() {
        super("Falcon 9", true, 0, 500, 22, 60);
    }

    @Override
    double calculateThrust() {
        return 1500;
    }
}
