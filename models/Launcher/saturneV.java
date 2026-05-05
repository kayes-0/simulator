class SaturneV extends Launcher {
    
    public SaturneV() {
        super("Saturne V", true, 0 ,2700, 140, 1500);
    }

    @Override
    double calculateThrust() {
        return 3500;
    }
}
