import network.ActivationFunction;
import network.Connection;
import network.NeuralNetwork;
import org.junit.Assert;
import org.junit.Test;
import util.Digit;
import util.Score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestSprint2 extends SetUp {

    @Test
    public void whenCallingTrain_thenInputNeuronsValuesAreSet() {
        Digit digit = new Digit(2, new double[][]{{2, 3}, {4, 5}});
        neuralNetwork.train(digit.getData(),digit.getLabel(), 1);
        for (int i = 0; i < inputNeurons.length; i++) {
            Assert.assertEquals(i + 2, inputNeurons[i].getValue(), 0);
        }
    }

    @Test
    public void whenCallingTest_thenInputNeuronsValuesAreSet() {
        Digit digit = new Digit(2, new double[][]{{2, 3}, {4, 5}});
        neuralNetwork.test(digit.getData());
        for (int i = 0; i < inputNeurons.length; i++) {
            Assert.assertEquals(i + 2, inputNeurons[i].getValue(), 0);
        }
    }

    @Test
    public void givenFourInputs_whenTrain_thenWeightsChange() {
        Digit digit = new Digit(2, new double[][]{{2, 3}, {4, 5}});
        List<Connection> actual = new ArrayList<>();
        connections.forEach(connection -> actual.add(new Connection(connection.getInputNeuron(), connection.getWeight())));
        neuralNetwork.train(digit.getData(),digit.getLabel(),1);
        for (int i = 0; i < 4; i++) {
            Assert.assertNotEquals(actual.get(i).getWeight(), connections.get(i).getWeight(), 0);
        }
    }

    public void givenFourInputs_whenLearnInOutputNeuron_thenWeightsAreUpdated() {
        List<Connection> actual = new ArrayList<>();
        connections.forEach(connection -> actual.add(new Connection(connection.getInputNeuron(), connection.getWeight())));
        actual.forEach(connection -> connection.addWeight(connection.getInputNeuron().getValue() * -1));
        outputNeurons[0].learn(6, 1);
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(actual.get(i).getWeight(), connections.get(i).getWeight(), 0);
        }
    }

    @Test
    public void whenCallingTest_thenReturnExpectedScoreArray() {
        Score[] result = neuralNetwork.test(new double[][]{{1, 2}, {3, 4}});
        Assert.assertEquals(0, result[0].getDIGIT());
        Assert.assertEquals(7, result[0].getProbability(), 0);
    }

    @Test
    public void whenCallingTestOnANetwork_thenReturnOrderedScoreArray() {
        NeuralNetwork test = new NeuralNetwork(4, 5, ActivationFunction.IDENTITY);
        for (int i = 0; i < test.getOutputNeurons().length; i++) {
            for (Connection connection: test.getOutputNeurons()[i].getConnections()) {
                connection.setWeight(Math.random());
            }
        }
        Score[] result = test.test(new double[][]{{1, 2}, {3, 4}});
        Arrays.stream(result).forEach(x -> System.out.println(x.getProbability()));
        double max = 0;
        for (int i = 0; i < result.length; i++) {
            if (i == 0) {
                max = result[i].getProbability();
            } else {
                Assert.assertTrue(max >= result[i].getProbability());
                max = result[i].getProbability();
            }
        }
    }
}
