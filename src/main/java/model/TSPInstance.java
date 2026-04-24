package model;

import java.util.List;

public class TSPInstance {

    private final String name;
    private final List<City> cities;
    private final double[][] distances;

    public TSPInstance(String name, List<City> cities, double[][] distances) {
        this.name = name;
        this.cities = cities;
        this.distances = distances;
    }

    public String getName() {
        return name;
    }

    public List<City> getCities() {
        return cities;
    }

    public double[][] getDistances() {
        return distances;
    }

    public int size() {
        return cities.size();
    }
}