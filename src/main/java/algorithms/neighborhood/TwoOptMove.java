package algorithms.neighborhood;

public class TwoOptMove {

    private final int i;
    private final int j;
    private final double delta;

    public TwoOptMove(int i, int j, double delta) {
        this.i = i;
        this.j = j;
        this.delta = delta;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getDelta() {
        return delta;
    }
}