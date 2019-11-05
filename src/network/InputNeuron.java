package network;


public class InputNeuron{

    private double value;

    /**
     * The input given from your data will be set here.
     *
     * @param value, the input for a Neuron, e.g. a pixel's gray value
     */
    public void setValue(double value) {
        this.value = value;
    }


    public double getValue() {
        return value;
    }
}

