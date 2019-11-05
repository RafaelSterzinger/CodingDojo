import network.Connection;
import network.InputNeuron;
import org.junit.Assert;
import org.junit.Test;

public class TestSprint1 extends SetUp {

    @Test
    public void givenFourInputValues_whenSetValues_thenInputNeuronsContainValues() {
        for (int i = 0; i < inputNeurons.length; i++) {
            Assert.assertEquals(i + 1, inputNeurons[i].getValue(), 0);
        }
    }

    @Test
    public void givenFourInputValues_whenGetValuesFromConnection_thenGetInputTimesWeight() {
        Assert.assertEquals(3, connections.get(0).getValue(), 0);
        Assert.assertEquals(-2, connections.get(1).getValue(), 0);
        Assert.assertEquals(6, connections.get(2).getValue(), 0);
        Assert.assertEquals(0, connections.get(3).getValue(), 0);
    }

    @Test
    public void givenFourInputValues_whenGetValueFromOutputNeuron_thenGetSumOfInputsTimesWeight() {
        Assert.assertEquals(7, outputNeurons[0].getValue(), 0);
    }

    @Test
    public void givenNeuralNetwork_whenCreateFullMesh_thenOutputNeuronContainsFourConnectionsWithInputNeurons() {
        Assert.assertEquals(4, connections.size());
        InputNeuron[] actual = new InputNeuron[4];
        int count = 0;
        for (Connection connection : connections) {
            actual[count++] = connection.getInputNeuron();
        }
        Assert.assertArrayEquals(inputNeurons, actual);
    }

}
