import java.io.*;
import java.time.LocalDateTime;
import java.util.*;
import Exception.*;

class Simulator {
    private static final double RANDOM_FAILURE_RATE = 0.05;
    private static final double FUEL_PRICE_PER_TON = 1200;

    private static Simulator instance;

    private final Scanner scanner = new Scanner(System.in);
    private final Random random = new Random();

    private final List<Launcher> launchers = new ArrayList<>();
    private final List<Pod> pods = new ArrayList<>();
    private final List<Booster> boosters = new ArrayList<>();
    private final List<Mission> missions = new ArrayList<>();
    private final List<Launch> history = new ArrayList<>();

    private Simulator() {
        loadCatalogs();
        loadHistory();
    }

    public static Simulator getInstance() {
        if (instance == null) {
            instance = new Simulator();
        }
        return instance;
    }

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

    private Launch simulateLaunch(Rocket rocket, Mission mission) {
        boolean success = true;
        String reason = "Launch successful";

        double requiredFuel = mission.calculateRequiredFuel(rocket);
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
                totalCost
        );
    }

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

    private void saveHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("history.txt"))) {
            for (Launch launch : history) {
                writer.println(launch.toFileLine());
            }
        } catch (IOException e) {
            System.out.println("Error while saving history.");
        }
    }

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