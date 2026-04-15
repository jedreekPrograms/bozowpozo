import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class Main {

    private static final String[] FILES = {
            "canada.tsp",
            "djibouti.tsp",
            "egypt.tsp",
            "ireland.tsp",
            "oman.tsp",
            "qatar.tsp",
            "tanzania.tsp",
            "uruguay.tsp",
            "western_sahara.tsp",
            "zimbabwe.tsp"
    };

    public static void main(String[] args) throws IOException {
        Loader loader = new Loader();

        ResultWriter.init();

        for (String file : FILES) {
            System.out.println("=================================");
            System.out.println("PLIK: " + file);

            List<City> cities = loader.load(Path.of("src/main/resources/" + file));

            if (cities.size() < 1000) {
                System.out.println("=== PARAMETRY SA ===");
                testSAParams(cities);

                System.out.println("=== PARAMETRY TABU ===");
                testTabuParams(cities);
            }

            runAlgo(file, "SA", cities, true);
            runAlgo(file, "TABU", cities, false);
        }

        ResultWriter.writeSummary();
    }

    private static void testSAParams(List<City> cities) {
        double[] temps = {1000, 5000, 10000};
        double[] cooling = {0.99, 0.995};

        for (double T : temps) {
            for (double c : cooling) {
                Route r = SimulatedAnnealing.solve(cities, T, c, 300, 200);
                System.out.println("T=" + T + " c=" + c + " -> " + r.distance());
            }
        }
    }

    private static void testTabuParams(List<City> cities) {
        int[] tabuSizes = {50, 100, 200};

        for (int size : tabuSizes) {
            Route r = TabuSearch.solve(cities, 1000, size, 100, 200);
            System.out.println("tabu=" + size + " -> " + r.distance());
        }
    }

    private static void runAlgo(String file, String name, List<City> cities, boolean sa) throws IOException {

        double sum = 0;
        double best = Double.MAX_VALUE;

        long start = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            Route r = sa
                    ? SimulatedAnnealing.solve(cities, 10000, 0.995, 500, 500)
                    : TabuSearch.solve(cities, 2000, 100, 100, 300);

            double d = r.distance();
            sum += d;
            best = Math.min(best, d);
        }

        long end = System.nanoTime();

        double avg = sum / 100;
        double time = (end - start) / 1_000_000.0;

        System.out.println(name);
        System.out.println("Best: " + best);
        System.out.println("Avg: " + avg);
        System.out.println("Time: " + time);

        ResultWriter.append(file, name, best, avg, time);
    }
}