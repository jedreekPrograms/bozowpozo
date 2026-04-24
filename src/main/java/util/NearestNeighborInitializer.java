package util;

import java.util.Random;

public class NearestNeighborInitializer {

    public static int[] generate(
            double[][] dist,
            Random random
    ) {

        int n = dist.length;

        boolean[] visited = new boolean[n];

        int[] path = new int[n];

        int current = random.nextInt(n);

        path[0] = current;

        visited[current] = true;

        for (int i = 1; i < n; i++) {

            int best = -1;
            double bestDist = Double.MAX_VALUE;

            for (int j = 0; j < n; j++) {

                if (!visited[j] &&
                        dist[current][j] < bestDist) {

                    bestDist = dist[current][j];
                    best = j;
                }
            }

            path[i] = best;

            visited[best] = true;

            current = best;
        }

        return path;
    }
}