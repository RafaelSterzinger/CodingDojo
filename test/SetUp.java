import network.*;
import org.junit.Before;

import java.util.List;

public abstract class SetUp {
    NeuralNetwork neuralNetwork;
    InputNeuron[] inputNeurons;
    OutputNeuron[] outputNeurons;
    List<Connection> connections;

    /**
     * Example from the slides
     */
    @Before
    public void setUp(){
        neuralNetwork = new NeuralNetwork(4,1, ActivationFunction.IDENTITY);
        inputNeurons = neuralNetwork.getInputNeurons();
        outputNeurons = neuralNetwork.getOutputNeurons();
        connections = outputNeurons[0].getConnections();

        inputNeurons[0].setValue(1);
        inputNeurons[1].setValue(2);
        inputNeurons[2].setValue(3);
        inputNeurons[3].setValue(4);

        connections.get(0).setWeight(3);
        connections.get(1).setWeight(-1);
        connections.get(2).setWeight(2);
        connections.get(3).setWeight(0);
    }
}
