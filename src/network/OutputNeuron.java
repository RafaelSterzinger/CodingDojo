package network;

import java.util.ArrayList;
import java.util.List;

public class OutputNeuron {
    private List<Connection> connections;
    private ActivationFunction function;

    public OutputNeuron(ActivationFunction activationFunction) {
        function = activationFunction;
        connections = new ArrayList<>();
    }

    /**
     * @param connection, between this outputNeuron and an inputNeuron
     */
    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    /**
     * Implements the Delta-Learning-Rule, which updates the weights of the edges accordingly.
     * You don't need to think about the derivative of the activationFunction if you use identity,
     * since using the IDENTITY results in 1.
     * The formula is: weight+=epsilon*(should-value of this)*value of input neuron
     * HINT: do not calculate the weighted sum for each connection or it will take forever!
     * (Optional) If you want to try further activationFunctions you need to use the derivative of the activation function.
     *
     * @param should,  is the target value
     * @param epsilon, is the learning rate which defines how "big of a step, you take in the steepest direction"
     */
    public void learn(double should, double epsilon) {
        // TODO 2
        double value=this.getValue();
        double error = should - value;
        for (Connection connection : this.getConnections()) {
            connection.addWeight(epsilon * error * function.getDerivative().activate(value) * connection.getInputNeuron().getValue());
        }
    }

    /**
     * (Optional) If you want to try further activationFunction you need to generalize by activating the sum here.
     *
     * @return value, the sum of all input times the weights
     */
    public double getValue() {
        // TODO 1
        return function.getActivationFunction().activate(connections.stream().mapToDouble(Connection::getValue).sum());
    }

    public List<Connection> getConnections() {
        return connections;
    }

}
