abstract class Launcher {
    String name;
    boolean crewed;
    int maxBoosters;
    double maxFuel;
    double maxPayload;
    double price;

    /**
     * @param name        nom du lanceur
     * @param crewed      si le lanceur est ok pour les vols habités
     * @param maxBoosters nombre maximum de boosters qu'il peut accueillir
     * @param maxFuel     capacité maximale en carburant (tonnes)
     * @param maxPayload  charge utile maximale admissible (tonnes)
     * @param price       prix de base du lanceur (millions EUR)
     */
    public Launcher(String name, boolean crewed, int maxBoosters, double maxFuel, double maxPayload, double price) {
        this.name = name;
        this.crewed = crewed;
        this.maxBoosters = maxBoosters;
        this.maxFuel = maxFuel;
        this.maxPayload = maxPayload;
        this.price = price;
    }

    /**
     * Calcule la poussée totale du lanceur en kilonewtons (kN).
     * @return poussée en kN
     */
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

    @Override
    public String toString() {
        return name + " | crewed: " + crewed
                + " | max boosters: " + maxBoosters
                + " | max fuel: " + maxFuel + " t"
                + " | max payload: " + maxPayload + " t"
                + " | price: " + price + " M EUR";
    }

}