package main;

import gui.GUI;
import network.ActivationFunction;
import network.NeuralNetwork;
import util.Digit;
import util.MNISTDecoder;
import util.Score;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        if (args.length == 0 || args[0].equals("cli")) {
            NeuralNetwork neuralNetwork = new NeuralNetwork(28 * 28, 10, ActivationFunction.IDENTITY);
            System.out.println("Loading " + MNISTDecoder.class);
            MNISTDecoder mnistDecoder = MNISTDecoder.getInstance();
            double cLearningParameter = 0.01;
            int totalIterations = 10;
            for (int i = 0; i < totalIterations; i++) {
                for (Digit digit : mnistDecoder.getTrainingData()) {
                    neuralNetwork.train(digit.getData(),digit.getLabel(), cLearningParameter);
                }
                int right = 0;
                int wrong = 0;
                for (Digit digit : mnistDecoder.getTestData()) {
                    Score[] scores = neuralNetwork.test(digit.getData());
                    if(scores != null) {
                        if (scores[0].getDIGIT() == digit.getLabel()) {
                            right++;
                        } else {
                            wrong++;
                        }
                    }
                }
                cLearningParameter *= 0.9;

                System.out.println("Numbers guessed correct: " + right);
                System.out.println("Numbers guessed wrong: " + wrong);
                System.out.println("Right/Wrong Ratio: " + ((double) right / (double) (wrong + right)));
                System.out.println("---------------------");
            }
        } else if (args[0].equals("gui")) {
            System.out.println("Loading " + MNISTDecoder.class);
            MNISTDecoder.getInstance();
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Java Coding Dojo");
                GUI gui = new GUI();
                frame.setContentPane(gui.contentPane);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 600);
                frame.setMinimumSize(new Dimension(500, 300));
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            });
        }
    }
}
