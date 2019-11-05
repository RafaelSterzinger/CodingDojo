package gui;

import network.ActivationFunction;
import network.Connection;
import network.NeuralNetwork;
import util.Digit;
import util.MNISTDecoder;
import util.Score;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI implements ResultCallback {
    public JTabbedPane contentPane;
    private MNISTDecoder mnistDecoder = MNISTDecoder.getInstance();
    private NeuralNetwork neuralNetwork;
    private JLabel lbCorrectClassified;
    private JLabel lbIncorrectClassified;
    private JLabel lbRatio;
    private JButton trainButton;
    private JTextField tfLearningRate;
    private JTextField tfIterations;
    private JTextField tfGradientDecentFactor;
    private JProgressBar trainingProgressBar;
    private JPanel mnistViewer;
    private JButton btNext;
    private JButton btPrevious;
    private JPanel paDigits;
    private JButton switchButton;
    private JSlider slider;
    private JButton clearButton;
    private JPanel paDraw;
    private JLabel lbResult;
    private List<ConnectionCanvas> connectionCanvasList;
    private boolean trainingRunning = false;
    private List<Digit> trainingData;
    private int trainingIndex;

    public GUI() {
        trainButton.addActionListener(actionEvent -> {
            if (trainingRunning) {
                onCancelTrain();
            } else {
                onTrain();
            }
        });
        btNext.addActionListener(actionEvent -> {
            trainingIndex++;
            updateTrainingIndex();
        });
        btPrevious.addActionListener(actionEvent -> {
            trainingIndex--;
            updateTrainingIndex();
        });
        switchButton.addActionListener(actionEvent -> {
            connectionCanvasList.forEach(connectionCanvas -> {
                connectionCanvas.toggleThreshold();
                connectionCanvas.update();
            });
        });
        slider.addChangeListener(changeEvent -> {
            double min = slider.getMinimum();
            double max = slider.getMaximum();
            assert min != max;
            double value = slider.getValue();
            final double threshold = (2 * value - 1) / (max - min);
            //
            // System.out.println(threshold);
            connectionCanvasList.forEach(connectionCanvas -> {
                connectionCanvas.setThreshold(threshold);
                connectionCanvas.update();
            });
        });
        clearButton.addActionListener(actionEvent -> ((DrawCanvas) paDraw).clear());
    }

    private void createUIComponents() {
        trainingData = MNISTDecoder.getInstance().getTrainingData();
        connectionCanvasList = new ArrayList<>();
        mnistViewer = new gui.Canvas();
        updateTrainingIndex();
        neuralNetwork = new NeuralNetwork(28 * 28, 10, ActivationFunction.IDENTITY);
        paDigits = new JPanel(new GridLayout(2, 5));
        for (int i = 0; i <= 9; i++) {
            List<Connection> connections = neuralNetwork.getOutputNeurons()[i].getConnections();
            ConnectionCanvas connectionCanvas = new ConnectionCanvas(connections);
            connectionCanvasList.add(connectionCanvas);
            paDigits.add(connectionCanvas);
        }
        paDraw = new DrawCanvas(neuralNetwork, this);
    }

    private void updateTrainingIndex() {
        if (trainingIndex < 0) {
            trainingIndex = 0;
        } else if (trainingIndex > trainingData.size() - 1) {
            trainingIndex = trainingData.size() - 1;
        }
        ((Canvas) mnistViewer).setData(trainingData.get(trainingIndex).getData());
    }

    private synchronized void onTrain() {
        try {
            double learningParameter = Double.parseDouble(tfLearningRate.getText());
            int iterations = Integer.parseInt(tfIterations.getText());
            double gradientDescent = Double.parseDouble(tfGradientDecentFactor.getText());
            trainingRunning = true;
            startTrainingThread(learningParameter, iterations, gradientDescent);
            trainButton.setText("Cancel Training");
        } catch (NumberFormatException ex) {
            showErrorMessage("Invalid Hyperparameter: " + ex.getMessage());
        }
    }

    private synchronized void onCancelTrain() {
        // continuous checks for trainingRunning in the TrainingThread will close the thread if trainingRunning is
        // set to false
        trainButton.setEnabled(false);
        trainingRunning = false;
        // button has to be disabled, will be enabled after TrainingThread is stopped
    }

    private void startTrainingThread(double learningParameter, int iterations, double gradientDescent) {
        new Thread(() -> {
            double cLearningParameter = learningParameter;
            int totalIterations =
                    iterations
                            * (mnistDecoder.getTestData().size() +
                            mnistDecoder.getTrainingData().size());
            int currentIteration = 0;
            for (int i = 0; i < iterations; i++) {
                for (Digit digit : mnistDecoder.getTrainingData()) {
                    if (!trainingRunning) {
                        break;
                    }
                    neuralNetwork.train(digit.getData(),digit.getLabel(), cLearningParameter);
                    currentIteration++;
                    trainingProgressBar.setValue((int) (1000.0 * currentIteration / totalIterations));
                }

                int right = 0;
                int wrong = 0;
                for (Digit digit : mnistDecoder.getTestData()) {
                    if (!trainingRunning) {
                        break;
                    }
                    Score[] scores = neuralNetwork.test(digit.getData());
                    if(scores!=null) {
                        if (scores[0].getDIGIT() == digit.getLabel()) {
                            right++;
                        } else {
                            wrong++;
                        }
                    }
                    currentIteration++;
                    trainingProgressBar.setValue((int) (1000.0 * currentIteration / totalIterations));

                }
                if (!trainingRunning) {
                    break;
                }
                lbCorrectClassified.setText("" + right);
                lbIncorrectClassified.setText("" + wrong);
                lbRatio.setText(String.format("%.2f", (double) right / (double) (wrong + right)));
                cLearningParameter *= gradientDescent;

                /*
                System.out.println("Numbers guessed correct: " + right);
                System.out.println("Numbers guessed wrong: " + wrong);
                System.out.println("Right/Wrong Ratio: " + ((double) right / (double) (wrong + right)));
                System.out.println(cLearningParameter);
                System.out.println("---------------------");
                 */
                connectionCanvasList.forEach(ConnectionCanvas::update);
            }
            trainButton.setText("Train!!!");
            trainingRunning = false;
            trainButton.setEnabled(true);
        }).start();
    }

    private void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(contentPane,
                errorMessage,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void onScore(Score[] scores) {
        StringBuilder sb = new StringBuilder("I think it is a ");
        Arrays.stream(scores).forEach(score -> sb.append(score.getDIGIT() + " "));
        sb.append(", in that order!");
        lbResult.setText(sb.toString());
    }
}
