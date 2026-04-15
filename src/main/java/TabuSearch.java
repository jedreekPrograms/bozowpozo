import java.util.*;

public class TabuSearch {

    private static final Random rand = new Random();

    public static Route solve(List<City> cities,
                              int iterations,
                              int tabuSize,
                              int neighbourhoodSize,
                              int noImproveLimit) {

        Route current = Route.random(cities);
        Route best = current;

        Queue<List<City>> tabuQueue = new LinkedList<>();
        Set<List<City>> tabuSet = new HashSet<>();

        int noImprove = 0;

        for (int it = 0; it < iterations && noImprove < noImproveLimit; it++) {

            Route bestNeighbour = null;

            for (int i = 0; i < neighbourhoodSize; i++) {
                Route neighbour = Route.neighbour(current);

                List<City> rep = neighbour.getCities();

                boolean isTabu = tabuSet.contains(rep);

                if (isTabu && neighbour.distance() >= best.distance()) {
                    continue;
                }

                if (bestNeighbour == null ||
                        neighbour.distance() < bestNeighbour.distance()) {
                    bestNeighbour = neighbour;
                }
            }

            if (bestNeighbour == null) continue;

            current = bestNeighbour;

            List<City> rep = current.getCities();
            tabuQueue.add(rep);
            tabuSet.add(rep);

            if (tabuQueue.size() > tabuSize) {
                List<City> removed = tabuQueue.poll();
                tabuSet.remove(removed);
            }

            if (current.distance() < best.distance()) {
                best = current;
                noImprove = 0;
            } else {
                noImprove++;
            }
        }

        return best;
    }
}