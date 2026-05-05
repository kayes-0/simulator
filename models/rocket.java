import java.util.ArrayList;
import java.util.List;

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

    public void addBooster(Booster booster, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.boosters.add(booster);
        }
    }

    public int getBoosterCount() {
        return boosters.size();
    }

    public String getName() {
        return launcher.getName()+ "+" + pod.getName();
    }

    public Pod getPod() {
        return pod;
    }

    public Launcher getLauncher() {
        return launcher;
    }

    public double calculateTotalMass() {
        double total = pod.getMass();

        for (Booster booster : boosters) {
            total += booster.getMass();
        }
        return total;
    }

    public double calculateTotalPrice() {
        double total = launcher.getPrice() + pod.getPrice();

        for (Booster booster : boosters) {
            total+= booster.getPrice();
        }
        return total;
    }
}