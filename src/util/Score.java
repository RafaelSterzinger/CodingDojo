package util;

public class Score implements Comparable<Score> {
    private int DIGIT;
    private double probability;

    public Score(int DIGIT, double probability) {
        this.DIGIT = DIGIT;
        this.probability = probability;
    }

    public int getDIGIT() {
        return DIGIT;
    }

    @Override
    public int compareTo(Score o) {
        return Double.compare(o.probability, this.probability);
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
