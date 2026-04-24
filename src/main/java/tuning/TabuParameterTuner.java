package tuning;

import algorithms.TabuSearch;
import model.TSPInstance;

public class TabuParameterTuner {

    public static TabuSearch tune(
            TSPInstance instance
    ) {

        /*
            large instances
        */
        if (instance.size() > 3000) {

            return new TabuSearch(
                    120,
                    50000,
                    1000,
                    5000
            );
        }

        /*
            medium instances
        */
        if (instance.size() > 1000) {

            return new TabuSearch(
                    80,
                    25000,
                    600,
                    2000
            );
        }

        /*
            tuning only for small instances
        */

        int[] tabuSizes = {
                20,
                50,
                100
        };

        int[] iterations = {
                5000,
                10000
        };

        int[] neighborhoodSizes = {
                100,
                300
        };

        double best =
                Double.MAX_VALUE;

        TabuSearch bestAlgo =
                null;

        for (int tabu : tabuSizes) {

            for (int iter : iterations) {

                for (int neigh :
                        neighborhoodSizes) {

                    TabuSearch ts =
                            new TabuSearch(
                                    tabu,
                                    iter,
                                    neigh,
                                    500
                            );

                    double avg = 0;

                    for (int r = 0;
                         r < 3;
                         r++) {

                        avg +=
                                ts.solve(instance)
                                        .getCost();
                    }

                    avg /= 3.0;

                    if (avg < best) {

                        best = avg;

                        bestAlgo = ts;
                    }
                }
            }
        }

        return bestAlgo;
    }
}