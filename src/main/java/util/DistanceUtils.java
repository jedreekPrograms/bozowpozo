package util;

public class DistanceUtils {

    public static double calculateCost(int[] path, double[][] dist) {

        double cost = 0;

        for (int i = 0; i < path.length - 1; i++) {
            cost += dist[path[i]][path[i + 1]];
        }

        cost += dist[path[path.length - 1]][path[0]];

        return cost;
    }
}