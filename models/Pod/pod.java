/**
 * Classe abstraite représentant une capsule spatiale (habité ou cargo).
 */
abstract class Pod {
    String name;
    boolean crewed;
    int maxPassengers;
    double weight;
    double price;

    /**
     * @param name          nom de la capsule
     * @param crewed        si la capsule est ok pour l'équipage
     * @param maxPassengers nombre maximum de passagers
     * @param weight        masse de la capsule (tonnes)
     * @param price         prix de la capsule (millions EUR)
     */
    public Pod(String name, boolean crewed, int maxPassengers, double weight, double price) {
        this.name = name;
        this.crewed = crewed;
        this.maxPassengers = maxPassengers;
        this.weight = weight;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public boolean isCrewed() {
        return crewed;
    }

    public int getMaxPassengers() {
        return maxPassengers;
    }

    public double getMass() {
        return weight;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " | crewed: " + crewed
                + " | max passengers: " + maxPassengers
                + " | mass: " + weight + " t"
                + " | price: " + price + " M EUR";
    }
}
