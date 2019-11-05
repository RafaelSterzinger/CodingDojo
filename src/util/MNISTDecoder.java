package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MNISTDecoder {

    private static MNISTDecoder instance;

    private  List<Digit> trainingData;
    private  List<Digit> testData;

    private static int toUnsignedByte(byte b) {
        return b & 0xFF;
    }

    public static MNISTDecoder getInstance(){
        if(instance==null) {
            instance=new MNISTDecoder();
            try {
                instance.trainingData = MNISTDecoder.loadDataSet(
                        "dataset/train-images-idx3-ubyte",
                        "dataset/train-labels-idx1-ubyte");

                instance.testData = MNISTDecoder.loadDataSet(
                        "dataset/t10k-images-idx3-ubyte",
                        "dataset/t10k-labels-idx1-ubyte");
            } catch (IOException | URISyntaxException ignored) {
            }
        }
        return instance;
    }

    private static List<Digit> loadDataSet(String dataPath, String labelPath) throws IOException, URISyntaxException {
        byte[] dataByte;
        byte[] labelByte;

        dataByte = Files.readAllBytes(Paths.get(MNISTDecoder.class.getResource(dataPath).toURI()));
        labelByte = Files.readAllBytes(Paths.get(MNISTDecoder.class.getResource(labelPath).toURI()));

        List<Digit> digits = new ArrayList<>();

        int readHeadData = 16;
        int readHeadLabel = 8;

        while (readHeadData < dataByte.length) {
            double[][] data = new double[28][28];
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    data[i][j] = toUnsignedByte(dataByte[readHeadData++])/255.0;
                }
            }
            int label = toUnsignedByte(labelByte[readHeadLabel++]);

            digits.add(new Digit(label, data));
        }
        return digits;
    }

    public List<Digit> getTrainingData() {
        return trainingData;
    }

    public List<Digit> getTestData() {
        return testData;
    }
}
