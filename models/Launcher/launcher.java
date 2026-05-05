abstract class Launcher {
    String name;
    boolean crewed;
    int maxBoosters;
    double maxFuel;
    double maxPayload;
    double price;

    public Launcher(String name, boolean crewed, int maxBoosters, double maxFuel, double maxPayload, double price) {
        this.name = name;
        this.crewed = crewed;
        this.maxBoosters = maxBoosters;
        this.maxFuel = maxFuel;
        this.maxPayload = maxPayload;
        this.price = price;
    }

    abstract double calculateThrust();

    public String getName() {
        return name;
    }

    public boolean isCrewed() {
        return crewed;
    }

    public int getMaxBoosters() {
        return maxBoosters;
    }

    public double getMaxFuel() {
        return maxFuel;
    }

    public double getMaxPayload() {
        return maxPayload;
    }

    public double getPrice() {
        return price;
    }

}