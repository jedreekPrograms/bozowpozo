import java.io.*;
import java.util.*;

public class ResultWriter {

    private static final String RESULTS = "results.csv";
    private static final String SUMMARY = "summary.csv";

    private static boolean initialized = false;

    private static final Map<String, List<Double>> avgMap = new HashMap<>();
    private static final Map<String, List<Double>> bestMap = new HashMap<>();
    private static final Map<String, List<Double>> timeMap = new HashMap<>();

    public static void init() throws IOException {
        if (!initialized) {
            try (BufferedWriter w = new BufferedWriter(new FileWriter(RESULTS))) {
                w.write("file,algorithm,best,avg,time_ms\n");
            }
            initialized = true;
        }
    }

    public static void append(String file, String algorithm,
                              double best, double avg, double time) throws IOException {

        try (BufferedWriter w = new BufferedWriter(new FileWriter(RESULTS, true))) {
            w.write(file + "," + algorithm + "," + best + "," + avg + "," + time + "\n");
        }

        // 🔥 FIX: separator
        String key = file + "|" + algorithm;

        avgMap.computeIfAbsent(key, k -> new ArrayList<>()).add(avg);
        bestMap.computeIfAbsent(key, k -> new ArrayList<>()).add(best);
        timeMap.computeIfAbsent(key, k -> new ArrayList<>()).add(time);
    }

    public static void writeSummary() throws IOException {
        try (BufferedWriter w = new BufferedWriter(new FileWriter(SUMMARY))) {
            w.write("file,algorithm,avg_of_avg,best_global,avg_time\n");

            List<String> keys = new ArrayList<>(avgMap.keySet());
            Collections.sort(keys);

            for (String key : keys) {
                String[] parts = key.split("\\|");
                String file = parts[0];
                String algo = parts[1];

                List<Double> avgs = avgMap.get(key);
                List<Double> bests = bestMap.get(key);
                List<Double> times = timeMap.get(key);

                double avgOfAvg = avgs.stream().mapToDouble(d -> d).average().orElse(0);
                double bestGlobal = bests.stream().mapToDouble(d -> d).min().orElse(0);
                double avgTime = times.stream().mapToDouble(d -> d).average().orElse(0);

                w.write(file + "," + algo + "," + avgOfAvg + "," + bestGlobal + "," + avgTime + "\n");
            }
        }
    }
}