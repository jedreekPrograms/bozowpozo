import java.util.List;
import java.util.Random;

public class SimulatedAnnealing {

    private static final Random rand = new Random();

    public static Route solve(List<City> cities,
                              double T,
                              double cooling,
                              int epochLength,
                              int maxEpochs) {

        Route current = Route.random(cities);
        Route best = current;

        int epoch = 0;

        while (epoch < maxEpochs && T > 1) {

            for (int i = 0; i < epochLength; i++) {

                Route next = Route.neighbour(current);

                double currDist = current.distance();
                double nextDist = next.distance();

                if (acceptance(currDist, nextDist, T) > rand.nextDouble()) {
                    current = next;
                    currDist = nextDist;
                }

                if (currDist < best.distance()) {
                    best = current;
                }
            }

            T *= cooling;
            epoch++;
        }

        return best;
    }

    private static double acceptance(double current, double next, double T) {
        if (next < current) return 1.0;
        return Math.exp((current - next) / T);
    }
}