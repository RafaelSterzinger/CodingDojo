package gui;

import network.NeuralNetwork;
import util.Score;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;

public class DrawCanvas extends Canvas {
    private boolean mousePressed;
    private NeuralNetwork neuralNetwork;
    private ResultCallback resultCallback;

    public DrawCanvas(NeuralNetwork neuralNetwork, ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
        this.neuralNetwork = neuralNetwork;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                mousePressed = true;
                onMouseMove(mouseEvent);
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                mousePressed = false;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mousePressed = false;
            }
        });

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                if (mousePressed) {
                    onMouseMove(mouseEvent);
                }
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                if (mousePressed) {
                    onMouseMove(mouseEvent);
                }
            }
        });
    }

    public void clear() {
        Arrays.stream(data).forEach(row -> Arrays.fill(row, 0.0));
        repaint();
    }

    private void onMouseMove(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        if (mousePressed && x >= borderWidth && x < borderWidth + size && y >= borderHeight && y < borderHeight + size) {
            final int tileSize = size / IMAGE_SIZE;
            int j = (x - borderWidth) / tileSize;
            int i = (y - borderHeight) / tileSize;
            data[i][j] = 1.0;
            repaint();
            Score[] scores = neuralNetwork.test(data);
            if(scores!=null) {
                resultCallback.onScore(scores);
            }
        }
    }
}
