abstract class Pod {
    String name;
    boolean crewed;
    int maxPassengers;
    double weight;
    double price;

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

}
