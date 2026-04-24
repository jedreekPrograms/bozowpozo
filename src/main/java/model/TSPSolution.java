package model;

import java.util.Arrays;

public class TSPSolution {

    private final int[] path;
    private double cost;

    public TSPSolution(int[] path, double cost) {
        this.path = path;
        this.cost = cost;
    }

    public int[] getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public TSPSolution copy() {
        return new TSPSolution(Arrays.copyOf(path, path.length), cost);
    }
}