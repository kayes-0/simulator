abstract class Booster {
    String name;
    int maxThrust;
    double fuelCapacity;
    double price;

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