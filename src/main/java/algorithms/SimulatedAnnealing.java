package algorithms;

import algorithms.localsearch.TwoOptLocalSearch;
import algorithms.neighborhood.TwoOptNeighborhood;
import model.TSPInstance;
import model.TSPSolution;
import util.DistanceUtils;
import util.NearestNeighborInitializer;

import java.util.Arrays;
import java.util.Random;

public class SimulatedAnnealing {

    private final double initialTemperature;

    private final double coolingRate;

    private final int iterationsPerEpoch;

    private final double minTemperature;

    private final int maxNoImprovementEpochs;

    private final Random random = new Random();

    public SimulatedAnnealing(
            double initialTemperature,
            double coolingRate,
            int iterationsPerEpoch,
            double minTemperature,
            int maxNoImprovementEpochs
    ) {

        this.initialTemperature = initialTemperature;
        this.coolingRate = coolingRate;
        this.iterationsPerEpoch = iterationsPerEpoch;
        this.minTemperature = minTemperature;
        this.maxNoImprovementEpochs = maxNoImprovementEpochs;
    }

    public TSPSolution solve(TSPInstance instance) {

        double[][] dist = instance.getDistances();

        int n = instance.size();

        /*
            nearest neighbor start
        */
        int[] currentPath =
                NearestNeighborInitializer.generate(
                        dist,
                        random
                );

        double currentCost =
                DistanceUtils.calculateCost(
                        currentPath,
                        dist
                );

        /*
            full local optimization before SA
        */
        currentCost =
                TwoOptLocalSearch.improve(
                        currentPath,
                        dist,
                        currentCost
                );

        int[] bestPath =
                Arrays.copyOf(currentPath, n);

        double bestCost = currentCost;

        double temperature =
                initialTemperature;

        int noImprovement = 0;

        while (temperature > minTemperature &&
                noImprovement < maxNoImprovementEpochs) {

            boolean improved = false;

            for (int iter = 0;
                 iter < iterationsPerEpoch;
                 iter++) {

                int i =
                        1 + random.nextInt(n - 2);

                int j =
                        1 + random.nextInt(n - 2);

                if (i > j) {

                    int tmp = i;
                    i = j;
                    j = tmp;
                }

                if (i == j) {
                    continue;
                }

                double delta =
                        TwoOptNeighborhood.calculateDelta(
                                currentPath,
                                dist,
                                i,
                                j
                        );

                if (delta < 0 ||
                        random.nextDouble()
                                < Math.exp(
                                -delta / temperature
                        )) {

                    TwoOptNeighborhood.applyTwoOpt(
                            currentPath,
                            i,
                            j
                    );

                    currentCost += delta;

                    if (currentCost < bestCost) {

                        bestCost = currentCost;

                        bestPath =
                                Arrays.copyOf(
                                        currentPath,
                                        n
                                );

                        improved = true;
                    }
                }
            }

            if (improved) {
                noImprovement = 0;
            } else {
                noImprovement++;
            }

            temperature *= coolingRate;
        }

        return new TSPSolution(
                bestPath,
                bestCost
        );
    }
}