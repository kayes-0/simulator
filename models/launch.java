import java.time.LocalDateTime;

/**
 * Historique représentant le résultat d'un lancement.
 * Contient la date, les noms de la fusée et de la mission, le statut
 * de réussite, la raison et le coût estimé.
 * Supporte la sérialisation CSV simple via {@link #toFileLine()} /
 * {@link #fromFileLine(String)}.
 */
class Launch {
    private final LocalDateTime date;
    private final String rocketName;
    private final String missionName;
    private final boolean success;
    private final String reason;
    private final double totalCost;

    public Launch(LocalDateTime date, Rocket rocket, Mission mission,
            boolean success, String reason, double totalCost) {
        this.date = date;
        this.rocketName = rocket.getName();
        this.missionName = mission.getName();
        this.success = success;
        this.reason = reason;
        this.totalCost = totalCost;
    }

    private Launch(LocalDateTime date, String rocketName, String missionName,
            boolean success, String reason, double totalCost) {
        this.date = date;
        this.rocketName = rocketName;
        this.missionName = missionName;
        this.success = success;
        this.reason = reason;
        this.totalCost = totalCost;
    }

    /**
     * Sérialise ce lancement en une ligne CSV (séparateur {@code ;}).
     * Format : {@code date;rocketName;missionName;success;reason;totalCost}
     * @return la ligne sérialisée
     */
    public String toFileLine() {
        return date + ";" + rocketName + ";" + missionName + ";"
                + success + ";" + reason + ";" + totalCost;
    }

    /**
     * Désérialise une ligne CSV en objet {@link Launch}.
     * Inverse de {@link #toFileLine()}.
     * @param line ligne lue depuis {@code history.txt}
     * @return l'objet {@code Launch} reconstitué
     * @throws ArrayIndexOutOfBoundsException si la ligne est mal formée
     */
    public static Launch fromFileLine(String line) {
        String[] parts = line.split(";");

        return new Launch(
                LocalDateTime.parse(parts[0]),
                parts[1],
                parts[2],
                Boolean.parseBoolean(parts[3]),
                parts[4],
                Double.parseDouble(parts[5]));
    }

    @Override
    public String toString() {
        return date + " | " + rocketName
                + " | " + missionName
                + " | " + (success ? "SUCCESS" : "FAILURE")
                + " | " + reason
                + " | Cost: " + totalCost + " M EUR";
    }
}