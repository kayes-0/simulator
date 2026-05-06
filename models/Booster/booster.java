/**
 * Classe abstraite représentant un booster d'appoint.
 * Les boosters augmentent la poussée au décollage mais alourdissent
 * la charge utile et ont un impact sur le coût total.
 */
abstract class Booster {
    String name;
    int maxThrust;
    double fuelCapacity;
    double price;

    /**
     * @param name         nom du booster
     * @param maxThrust    poussée maximale du booster (kN)
     * @param fuelCapacity capacité en carburant / masse du booster (tonnes)
     * @param price        prix unitaire du booster (millions EUR)
     */
    public Booster(String name, int maxThrust, double fuelCapacity, double price) {
        this.name = name;
        this.maxThrust = maxThrust;
        this.fuelCapacity = fuelCapacity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getMaxThrust() {
        return maxThrust;
    }

    /**
     * Retourne la masse du booster.
     * @return masse en tonnes
     */
    public double getMass() {
        return fuelCapacity;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " | thrust: " + maxThrust
                + " kN | mass: " + fuelCapacity
                + " t | price: " + price + " M EUR";
    }
}