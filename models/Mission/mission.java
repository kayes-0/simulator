/**
 * Classe abstraite représentant une mission spatiale.
 * Définit la destination, les contraintes (mission habitée ou non)
 * et la méthode de calcul du carburant nécessaire.
 */
abstract class Mission {
    String name;
    boolean crewed;
    double distance;
    double coefficient;

    /**
     * @param name        nom de la mission
     * @param crewed      si la mission nécessite une capsule habitée
     * @param distance    distance approximative jusqu'à la destination (km)
     * @param coefficient coefficient multiplicateur pour le calcul de carburant
     */
    public Mission(String name, boolean crewed, double distance, double coefficient) {
        this.name = name;
        this.crewed = crewed;
        this.distance = distance;
        this.coefficient = coefficient;
    }

    /**
     * Calcule le carburant nécessaire en fonction de la masse de la fusée.
     * @param mass masse totale de la charge utile (tonnes)
     * @return quantité de carburant requise (tonnes)
     */
    abstract double calculateFuel(double mass);

    public String getName() {
        return name;
    }

    public boolean isCrewedRequired() {
        return crewed;
    }

    /**
     * Point d'entrée pour calculer le carburant depuis une {@link Rocket}.
     * Délègue à {@link #calculateFuel(double)} avec la masse totale de la fusée.
     *
     * @param rocket la fusée assemblée
     * @return carburant requis en tonnes
     */
    public double calculateRequiredFuel(Rocket rocket) {
        return calculateFuel(rocket.calculateTotalMass());
    }

    @Override
    public String toString() {
        return name + " | crewed required: " + crewed
                + " | distance: " + distance + " km"
                + " | fuel coefficient: " + coefficient;
    }
}
