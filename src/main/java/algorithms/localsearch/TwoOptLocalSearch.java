package algorithms.localsearch;

import algorithms.neighborhood.TwoOptNeighborhood;

public class TwoOptLocalSearch {

    public static double improve(
            int[] path,
            double[][] dist,
            double currentCost
    ) {

        int n = path.length;

        boolean improved = true;

        while (improved) {

            improved = false;

            for (int i = 1; i < n - 2; i++) {

                for (int j = i + 1; j < n - 1; j++) {

                    double delta =
                            TwoOptNeighborhood.calculateDelta(
                                    path,
                                    dist,
                                    i,
                                    j
                            );

                    if (delta < 0) {

                        TwoOptNeighborhood.applyTwoOpt(
                                path,
                                i,
                                j
                        );

                        currentCost += delta;

                        improved = true;
                    }
                }
            }
        }

        return currentCost;
    }
}