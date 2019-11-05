package util;

public class Digit {
    private int label;
    private double[][] data;

    /**
     * Object representing an entry of the MNIST-dataset
     *
     * @param label the target value => a digit between 0 and 9
     * @param data a 28*28 array containing greyscale pixels
     */
    public Digit(int label, double[][] data) {
        this.label = label;
        this.data = data;
    }

    public String toString() {
        return "Digit{" +
                "label=" + label +
                '}';
    }

    public int getLabel() {
        return label;
    }

    public double[][] getData() {
        return data;
    }
}
