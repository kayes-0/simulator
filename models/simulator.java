import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import Exception.*;

/* Cœur du simulateur de lancement spatial.
* Implémente le pattern Singleton pour garantir une instance unique.
* Gère le menu, le déroulement d'un lancement et la persistance
* de l'historique dans un fichier texte.
*/

class Simulator {
    /**
     * Probabilité (5%) qu'un lancement échoue de façon aléatoire, indépendamment
     * des conditions techniques.
     */
    private static final double RANDOM_FAILURE_RATE = 0.05;
    /**
     * Prix du carburant en EUR par tonne. Utilisé pour estimer le coût total du
     * lancement.
     */
    private static final double FUEL_PRICE_PER_TON = 1200;

    /** Instance unique du simulateur (pattern Singleton). */
    private static Simulator instance;

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    private final List<Launcher> launchers = new ArrayList<>();
    private final List<Pod> pods = new ArrayList<>();
    private final List<Booster> boosters = new ArrayList<>();
    private final List<Mission> missions = new ArrayList<>();
    private final List<Launch> history = new ArrayList<>();

    /**
     * Constructeur privé — accès réservé via {@link #getInstance()}.
     * Initialise les catalogues et charge l'historique depuis le fichier.
     */
    private Simulator() {
        loadCatalogs();
        loadHistory();
    }

    /**
     * Retourne l'instance unique du simulateur.
     * Crée l'instance à la première invocation (lazy initialization).
     * @return l'instance Singleton de {@code Simulator}
     */
    public static Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

    /**
     * Boucle principale du simulateur.
     * Affiche le menu et délègue les actions jusqu'à ce que l'utilisateur quitte.
     */
    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Space Launch Simulator ===");
            System.out.println("1. Start a launch");
            System.out.println("2. Show history");
            System.out.println("0. Quit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> startLaunchProcess();
                case "2" -> showHistory();
                case "0" -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Initialise les catalogues d'équipements disponibles :
     * lanceurs, capsules, boosters et missions.
     * Appelé une seule fois à la construction du simulateur.
     */
    private void loadCatalogs() {
        launchers.add(new SaturneV());
        launchers.add(new Ariane5());
        launchers.add(new Falcon9());
        launchers.add(new Sls());

        pods.add(new Orion());
        pods.add(new CrewDragon());
        pods.add(new Apollo());
        pods.add(new CargoDragon());

        boosters.add(new EapAriane());
        boosters.add(new SrbShuttle());
        boosters.add(new Be3());

        missions.add(new Orbit());
        missions.add(new Iss());
        missions.add(new Moon());
        missions.add(new Mars());
        missions.add(new Pluto());
    }

    /**
     * Orchestre le processus de configuration d'un lancement :
     * sélection du lanceur, de la capsule, des boosters et de la mission.
     * Lance la simulation et enregistre le résultat dans l'historique.
     */
    private void startLaunchProcess() {
        Launcher launcher = chooseFromList(launchers, "Choose a launcher");
        Pod pod = chooseFromList(pods, "Choose a pod");

        Rocket rocket = new Rocket(launcher, pod);

        System.out.print("How many boosters do you want to add? ");
        int boosterCount = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < boosterCount; i++) {
            Booster booster = chooseFromList(boosters, "Choose booster " + (i + 1));
            rocket.addBooster(booster);
        }

        Mission mission = chooseFromList(missions, "Choose a mission");

        Launch launch = simulateLaunch(rocket, mission);
        history.add(launch);
        saveHistory();

        System.out.println("\n=== Launch result ===");
        System.out.println(launch);
    }

    /**
     * Simule le lancement et détermine son succès ou son échec.
     * Vérifie dans l'ordre :
     * 1. La capacité carburant du lanceur
     * 2. La limite de charge utile
     * 3. Le nombre maximum de boosters autorisés
     * 4. La compatibilité capsule/mission habitée
     * 5. Une panne aléatoire avec probabilité {@value #RANDOM_FAILURE_RATE}
     * @param rocket  la fusée assemblée pour ce lancement
     * @param mission la mission cible
     * @return un objet {@link Launch} contenant le résultat et le coût estimé
     */
    private Launch simulateLaunch(Rocket rocket, Mission mission) {
        boolean success = true;
        String reason = "Launch successful";

        double requiredFuel = mission.calculateRequiredFuel(rocket);
        // Coût total = prix matériel + coût carburant (converti de EUR/t en M EUR)
        double totalCost = rocket.calculateTotalPrice() + (requiredFuel * FUEL_PRICE_PER_TON / 1_000_000);

        try {
            if (requiredFuel > rocket.getLauncher().getMaxFuel()) {
                throw new NotEnoughFuelException("Not enough fuel capacity");
            }

            if (rocket.calculateTotalMass() > rocket.getLauncher().getMaxPayload()) {
                success = false;
                reason = "Payload limit exceeded";
            } else if (rocket.getBoosterCount() > rocket.getLauncher().getMaxBoosters()) {
                success = false;
                reason = "Too many boosters";
            } else if (mission.isCrewedRequired() && !rocket.getPod().isCrewed()) {
                success = false;
                reason = "Pod incompatible with crewed mission";
                // Anomalie technique imprévue
            } else if (random.nextDouble() < RANDOM_FAILURE_RATE) {
                success = false;
                reason = "Unexpected technical anomaly";
            }

        } catch (NotEnoughFuelException e) {
            success = false;
            reason = e.getMessage();
        }

        return new Launch(
                LocalDateTime.now(),
                rocket,
                mission,
                success,
                reason,
                totalCost);
    }

    /**
     * Affiche une liste numérotée et invite l'utilisateur à choisir un élément.
     * La saisie est validée en boucle jusqu'à obtenir un entier dans la plage
     * valide.
     * @param <T>   type des éléments de la liste
     * @param list  liste d'options à présenter
     * @param title intitulé affiché avant la liste
     * @return l'élément sélectionné par l'utilisateur
     */
    private <T> T chooseFromList(List<T> list, String title) {
        System.out.println("\n" + title);

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }

        int choice = -1;

        while (choice < 1 || choice > list.size()) {
            System.out.print("Choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }

        return list.get(choice - 1);
    }

    private void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No launch history yet.");
            return;
        }

        for (Launch launch : history) {
            System.out.println(launch);
        }
    }

    /**
     * Persiste l'historique des lancements dans le fichier {@code history.txt}.
     * Chaque lancement est sérialisé sur une ligne via {@link Launch#toFileLine()}.
     * Le fichier est entièrement réécrit à chaque appel.
     */
    private void saveHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("history.txt"))) {
            for (Launch launch : history) {
                writer.println(launch.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Error while saving history.");
        }
    }

    /**
     * Charge l'historique des lancements depuis {@code history.txt} au démarrage.
     * Chaque ligne est désérialisée via {@link Launch#fromFileLine(String)}.
     */
    private void loadHistory() {
        File file = new File("history.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                history.add(Launch.fromFileLine(line));
            }

        } catch (IOException e) {
            System.out.println("Error while loading history.");
        }
    }
}