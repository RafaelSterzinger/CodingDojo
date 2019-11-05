package gui;

import network.Connection;

import java.util.List;

public class ConnectionCanvas extends Canvas {
    private List<Connection> connections;
    private boolean showThreshold = false;
    private double threshold;

    public ConnectionCanvas(List<Connection> connections) {
        this.connections = connections;
        update();
    }

    public void toggleThreshold() {
        this.showThreshold = !this.showThreshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public void update() {
        assert IMAGE_SIZE * IMAGE_SIZE == connections.size();
        double min = connections.stream().mapToDouble(Connection::getWeight).min().orElse(0);
        double max = connections.stream().mapToDouble(Connection::getWeight).max().orElse(0);
        // System.out.printf("Min weight: %f Max weight: %f\n", min, max);
        double range = max - min;
        for (int i = 0; i < connections.size(); i++) {
            double value = connections.get(i).getWeight();
            int x = i / IMAGE_SIZE;
            int y = i % IMAGE_SIZE;
            if (range == 0) {
                data[x][y] = 0;
            } else {
                if (showThreshold) {
                    data[x][y] = value < threshold ? 0 : 1;
                } else {
                    data[x][y] = (value - min) / range;
                }
            }
        }
        repaint();
    }

}
