package network;

public class Connection {
    private InputNeuron inputNeuron;
    private double weight;

    public Connection(InputNeuron inputNeuron){
        this(inputNeuron, 0.0);
    }

    public Connection(InputNeuron inputNeuron, double weight) {
        this.inputNeuron = inputNeuron;
        this.weight = weight;
    }

    /**
     * @return the InputNeuron, which will be needed for the Delta-Learning-Rule
     */
    public InputNeuron getInputNeuron() {
        return inputNeuron;
    }

    /**
     * @return input value times weight
     */
    public double getValue() {
        // TODO 1
        return 0.0;
    }

    /**
     * @param delta, the weight which should be added to the existing weight
     */
    public void addWeight(double delta) {
        // TODO 2
    }

    /**
     * @return the current weight of the connection
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Set a new weight, should NOT be needed for implementation
     *
     * @param weight
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Connection{" +
                "weight=" + weight +
                '}';
    }
}

