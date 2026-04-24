package algorithms;

import algorithms.localsearch.TwoOptLocalSearch;
import algorithms.neighborhood.TwoOptNeighborhood;
import model.TSPInstance;
import model.TSPSolution;
import util.DistanceUtils;
import util.NearestNeighborInitializer;

import java.util.Arrays;
import java.util.Random;

public class TabuSearch {

    private final int tabuTenure;

    private final int maxIterations;

    private final int neighborhoodSize;

    private final int maxNoImprovement;

    private final Random random = new Random();

    public TabuSearch(
            int tabuTenure,
            int maxIterations,
            int neighborhoodSize,
            int maxNoImprovement
    ) {

        this.tabuTenure = tabuTenure;
        this.maxIterations = maxIterations;
        this.neighborhoodSize = neighborhoodSize;
        this.maxNoImprovement = maxNoImprovement;
    }

    public TSPSolution solve(TSPInstance instance) {

        double[][] dist = instance.getDistances();

        int n = instance.size();

        /*
            nearest neighbor start
        */
        int[] current =
                NearestNeighborInitializer.generate(
                        dist,
                        random
                );

        double currentCost =
                DistanceUtils.calculateCost(
                        current,
                        dist
                );

        /*
            strong local optimization
        */
        currentCost =
                TwoOptLocalSearch.improve(
                        current,
                        dist,
                        currentCost
                );

        int[] best =
                Arrays.copyOf(current, n);

        double bestCost = currentCost;

        int[][] tabu =
                new int[n][n];

        int noImprovement = 0;

        for (int iteration = 0;
             iteration < maxIterations &&
                     noImprovement < maxNoImprovement;
             iteration++) {

            double bestNeighborCost =
                    Double.MAX_VALUE;

            int bestI = -1;
            int bestJ = -1;

            for (int k = 0;
                 k < neighborhoodSize;
                 k++) {

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
                                current,
                                dist,
                                i,
                                j
                        );

                double candidateCost =
                        currentCost + delta;

                boolean isTabu =
                        tabu[i][j] > iteration;

                /*
                    aspiration criterion
                */
                if (isTabu &&
                        candidateCost >= bestCost) {

                    continue;
                }

                if (candidateCost <
                        bestNeighborCost) {

                    bestNeighborCost =
                            candidateCost;

                    bestI = i;
                    bestJ = j;
                }
            }

            if (bestI == -1) {
                continue;
            }

            TwoOptNeighborhood.applyTwoOpt(
                    current,
                    bestI,
                    bestJ
            );

            currentCost =
                    bestNeighborCost;

            tabu[bestI][bestJ] =
                    iteration + tabuTenure;

            if (currentCost < bestCost) {

                bestCost = currentCost;

                best =
                        Arrays.copyOf(
                                current,
                                n
                        );

                noImprovement = 0;

            } else {

                noImprovement++;
            }
        }

        return new TSPSolution(
                best,
                bestCost
        );
    }
}