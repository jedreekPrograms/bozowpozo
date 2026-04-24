package parser;

import model.City;
import model.TSPInstance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TSPParser {

    public static TSPInstance load(String resourceName) throws IOException {

        InputStream is = TSPParser.class.getClassLoader()
                .getResourceAsStream(resourceName);

        if (is == null) {
            throw new RuntimeException("File not found: " + resourceName);
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        List<City> cities = new ArrayList<>();

        String line;
        boolean readCoords = false;

        while ((line = br.readLine()) != null) {

            line = line.trim();

            if (line.startsWith("NODE_COORD_SECTION")) {
                readCoords = true;
                continue;
            }

            if (line.startsWith("EOF")) {
                break;
            }

            if (readCoords) {

                String[] split = line.split("\\s+");

                if (split.length >= 3) {
                    int id = Integer.parseInt(split[0]);
                    double x = Double.parseDouble(split[1]);
                    double y = Double.parseDouble(split[2]);

                    cities.add(new City(id - 1, x, y));
                }
            }
        }

        double[][] distances = buildDistanceMatrix(cities);

        return new TSPInstance(resourceName, cities, distances);
    }

    private static double[][] buildDistanceMatrix(List<City> cities) {

        int n = cities.size();
        double[][] dist = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                double dx = cities.get(i).getX() - cities.get(j).getX();
                double dy = cities.get(i).getY() - cities.get(j).getY();

                dist[i][j] = Math.round(Math.sqrt(dx * dx + dy * dy));
            }
        }

        return dist;
    }
}