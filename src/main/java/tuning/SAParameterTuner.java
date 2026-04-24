package tuning;

import algorithms.SimulatedAnnealing;
import model.TSPInstance;

public class SAParameterTuner {

    public static SimulatedAnnealing tune(
            TSPInstance instance
    ) {

        /*
            large instances
        */
        if (instance.size() > 3000) {

            return new SimulatedAnnealing(
                    20000,
                    0.997,
                    10000,
                    0.001,
                    80
            );
        }

        /*
            medium instances
        */
        if (instance.size() > 1000) {

            return new SimulatedAnnealing(
                    10000,
                    0.995,
                    5000,
                    0.001,
                    50
            );
        }

        /*
            tuning only for small instances
        */

        double[] temps = {
                3000,
                5000,
                10000
        };

        double[] cooling = {
                0.995,
                0.997
        };

        int[] iterations = {
                1000,
                3000
        };

        double best =
                Double.MAX_VALUE;

        SimulatedAnnealing bestAlgo =
                null;

        for (double t : temps) {

            for (double c : cooling) {

                for (int it : iterations) {

                    SimulatedAnnealing sa =
                            new SimulatedAnnealing(
                                    t,
                                    c,
                                    it,
                                    0.001,
                                    30
                            );

                    double avg = 0;

                    for (int r = 0;
                         r < 3;
                         r++) {

                        avg +=
                                sa.solve(instance)
                                        .getCost();
                    }

                    avg /= 3.0;

                    if (avg < best) {

                        best = avg;

                        bestAlgo = sa;
                    }
                }
            }
        }

        return bestAlgo;
    }
}