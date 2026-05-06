import java.util.ArrayList;
import java.util.List;

/**
 * Représente une fusée assemblée pour un lancement.
 * Une fusée est composée d'un lanceur, d'une capsule et de boosters
 * optionnels.
 */
class Rocket {
    private Launcher launcher;
    private Pod pod;
    private List<Booster> boosters;

    public Rocket(Launcher launcher, Pod pod) {
        this.launcher = launcher;
        this.pod = pod;
        this.boosters = new ArrayList<>();
    }

    public Rocket(Launcher launcher, Pod pod, List<Booster> boosters) {
        this.launcher = launcher;
        this.pod = pod;
        this.boosters = new ArrayList<>(boosters);
    }

    public void addBooster(Booster booster) {
        this.boosters.add(booster);
    }

    /**
     * Ajoute plusieurs exemplaires du même booster à la fusée.
     * @param booster  le booster à ajouter
     * @param quantity nombre de fois qu'il doit être ajouté
     */
    public void addBooster(Booster booster, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.boosters.add(booster);
        }
    }

    public int getBoosterCount() {
        return boosters.size();
    }

    public String getName() {
        return launcher.getName() + "+" + pod.getName();
    }

    public Pod getPod() {
        return pod;
    }

    public Launcher getLauncher() {
        return launcher;
    }

    /**
     * Calcule la masse totale de la charge utile : capsule + boosters.
     * Le lanceur lui-même n'est pas compté (il est le vecteur, pas la charge).
     * @return masse totale en tonnes
     */
    public double calculateTotalMass() {
        double total = pod.getMass();

        for (Booster booster : boosters) {
            total += booster.getMass();
        }
        return total;
    }

    /**
     * Calcule le coût matériel total du lancement : lanceur + capsule + boosters.
     * Le carburant est calculé séparément dans {@link Simulator}.
     * @return coût total en millions EUR
     */
    public double calculateTotalPrice() {
        double total = launcher.getPrice() + pod.getPrice();

        for (Booster booster : boosters) {
            total += booster.getPrice();
        }
        return total;
    }
}