package algorithms.neighborhood;

public class TwoOptNeighborhood {

    public static void applyTwoOpt(
            int[] path,
            int i,
            int j
    ) {

        while (i < j) {

            int temp = path[i];
            path[i] = path[j];
            path[j] = temp;

            i++;
            j--;
        }
    }

    /*
        O(1) delta evaluation

        zamiast liczyć całą trasę od nowa
    */
    public static double calculateDelta(
            int[] path,
            double[][] dist,
            int i,
            int j
    ) {

        int n = path.length;

        int a = path[(i - 1 + n) % n];
        int b = path[i];

        int c = path[j];
        int d = path[(j + 1) % n];

        double removed =
                dist[a][b] +
                        dist[c][d];

        double added =
                dist[a][c] +
                        dist[b][d];

        return added - removed;
    }
}