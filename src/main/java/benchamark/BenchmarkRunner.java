package benchamark;

import algorithms.SimulatedAnnealing;
import algorithms.TabuSearch;
import model.TSPInstance;
import model.TSPSolution;
import parser.TSPParser;
import tuning.SAParameterTuner;
import tuning.TabuParameterTuner;
import util.CSVWriter;
import util.RouteExporter;

import java.util.ArrayList;
import java.util.List;

public class BenchmarkRunner {

    /*
        zgodnie z poleceniem:

        - małe dane (<1000):
            służą do testowania parametrów

        - duże dane:
            100 prób
    */

    private static final int LARGE_INSTANCE_RUNS = 100;

    private static final String[] INSTANCES = {

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

    public static void run() throws Exception {

        CSVWriter.append(

                "results.csv",

                "instance,algorithm,best,average,time_ms"
        );

        for (String instanceName : INSTANCES) {

            TSPInstance instance =
                    TSPParser.load(instanceName);

            System.out.println(
                    "\n===================================="
            );

            System.out.println(
                    "INSTANCE: "
                            + instanceName
            );

            System.out.println(
                    "Cities: "
                            + instance.size()
            );

            System.out.println(
                    "====================================\n"
            );

            /*
                zgodnie z poleceniem:

                małe instancje:
                tuning parametrów

                duże instancje:
                100 prób benchmarkowych
            */

            if (instance.size() < 1000) {

                System.out.println(
                        "SMALL INSTANCE -> PARAMETER TUNING"
                );

                SAParameterTuner.tune(instance);

                TabuParameterTuner.tune(instance);

                System.out.println(
                        "TUNING FINISHED\n"
                );

            } else {

                runSA(instance);

                runTabu(instance);
            }
        }
    }

    private static void runSA(
            TSPInstance instance
    ) {

        System.out.println(
                "STARTING SIMULATED ANNEALING..."
        );

        SimulatedAnnealing sa =
                SAParameterTuner.tune(instance);

        List<Double> results =
                new ArrayList<>();

        double best =
                Double.MAX_VALUE;

        TSPSolution bestSolution =
                null;

        long start =
                System.currentTimeMillis();

        /*
            zgodnie z poleceniem:
            100 prób dla dużych danych
        */
        for (int i = 0;
             i < LARGE_INSTANCE_RUNS;
             i++) {

            TSPSolution solution =
                    sa.solve(instance);

            double cost =
                    solution.getCost();

            results.add(cost);

            if (cost < best) {

                best = cost;

                bestSolution =
                        solution;
            }

            if ((i + 1) % 10 == 0) {

                System.out.println(

                        "SA progress: "

                                + (i + 1)

                                + "/"

                                + LARGE_INSTANCE_RUNS

                                + " | current best = "

                                + best
                );
            }
        }

        long end =
                System.currentTimeMillis();

        double average =
                results.stream()
                        .mapToDouble(
                                Double::doubleValue
                        )
                        .average()
                        .orElse(0);

        long time =
                end - start;

        /*
            zapis wyników
        */
        CSVWriter.append(

                "results.csv",

                instance.getName()

                        + ",SA,"

                        + best

                        + ","

                        + average

                        + ","

                        + time
        );

        /*
            zapis najlepszej trasy
        */
        RouteExporter.export(

                "routes/"
                        + instance.getName()
                        + "_SA.csv",

                instance,

                bestSolution
        );

        System.out.println(
                "\nSA FINISHED"
        );

        System.out.println(
                "BEST: "
                        + best
        );

        System.out.println(
                "AVERAGE: "
                        + average
        );

        System.out.println(
                "TIME(ms): "
                        + time
        );

        System.out.println();
    }

    private static void runTabu(
            TSPInstance instance
    ) {

        System.out.println(
                "STARTING TABU SEARCH..."
        );

        TabuSearch tabu =
                TabuParameterTuner.tune(
                        instance
                );

        List<Double> results =
                new ArrayList<>();

        double best =
                Double.MAX_VALUE;

        TSPSolution bestSolution =
                null;

        long start =
                System.currentTimeMillis();

        /*
            zgodnie z poleceniem:
            100 prób dla dużych danych
        */
        for (int i = 0;
             i < LARGE_INSTANCE_RUNS;
             i++) {

            TSPSolution solution =
                    tabu.solve(instance);

            double cost =
                    solution.getCost();

            results.add(cost);

            if (cost < best) {

                best = cost;

                bestSolution =
                        solution;
            }

            if ((i + 1) % 10 == 0) {

                System.out.println(

                        "TABU progress: "

                                + (i + 1)

                                + "/"

                                + LARGE_INSTANCE_RUNS

                                + " | current best = "

                                + best
                );
            }
        }

        long end =
                System.currentTimeMillis();

        double average =
                results.stream()
                        .mapToDouble(
                                Double::doubleValue
                        )
                        .average()
                        .orElse(0);

        long time =
                end - start;

        /*
            zapis wyników
        */
        CSVWriter.append(

                "results.csv",

                instance.getName()

                        + ",TABU,"

                        + best

                        + ","

                        + average

                        + ","

                        + time
        );

        /*
            zapis najlepszej trasy
        */
        RouteExporter.export(

                "routes/"
                        + instance.getName()
                        + "_TABU.csv",

                instance,

                bestSolution
        );

        System.out.println(
                "\nTABU FINISHED"
        );

        System.out.println(
                "BEST: "
                        + best
        );

        System.out.println(
                "AVERAGE: "
                        + average
        );

        System.out.println(
                "TIME(ms): "
                        + time
        );

        System.out.println();
    }
}