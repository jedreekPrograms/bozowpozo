import java.util.*;

public class Route {
    private final List<City> cities;
    private final double distance;
    private static final Random rand = new Random();

    public Route(List<City> cities) {
        this.cities = new ArrayList<>(cities);
        this.distance = calculateDistance();
    }

    public static Route random(List<City> cities) {
        List<City> shuffled = new ArrayList<>(cities);
        Collections.shuffle(shuffled);
        return new Route(shuffled);
    }

    public double distance() {
        return distance;
    }

    public List<City> getCities() {
        return new ArrayList<>(cities);
    }

    private double calculateDistance() {
        double sum = 0.0;

        for (int i = 0; i < cities.size() - 1; i++) {
            sum += dist(cities.get(i), cities.get(i + 1));
        }

        sum += dist(cities.get(cities.size() - 1), cities.get(0));
        return sum;
    }

    private double dist(City a, City b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    // 🔥 lepszy 2-opt
    public static Route neighbour(Route current) {
        List<City> newCities = current.getCities();

        int size = newCities.size();
        int i = rand.nextInt(size);
        int j = rand.nextInt(size);

        while (i == j) {
            j = rand.nextInt(size);
        }

        if (i > j) {
            int tmp = i;
            i = j;
            j = tmp;
        }

        Collections.reverse(newCities.subList(i, j));
        return new Route(newCities);
    }
}