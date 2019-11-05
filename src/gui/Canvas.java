package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Canvas extends JPanel {
    protected static final int IMAGE_SIZE = 28;
    protected double[][] data;

    protected int borderWidth;
    protected int borderHeight;
    protected int size;


    public Canvas() {
        this(new double[IMAGE_SIZE][IMAGE_SIZE]);

    }

    public Canvas(double[][] data) {
        this.data = data;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                onResize();
            }
        });
    }

    public void onResize() {
        if (getWidth() < getHeight()) {
            int rest = (getWidth() % IMAGE_SIZE);
            size = getWidth() - rest;
            borderHeight = rest / 2 + (getHeight() - getWidth()) / 2;
        } else {
            int rest = (getHeight() % IMAGE_SIZE);
            size = getHeight() - rest;
            borderWidth = rest / 2 + (getWidth() - getHeight()) / 2;
        }
    }

    public void setData(double[][] data) {
        assert data.length * data[0].length == IMAGE_SIZE * IMAGE_SIZE;
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        assert data.length * data[0].length == IMAGE_SIZE * IMAGE_SIZE;
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(borderWidth, borderHeight, size, size);
        g.translate(borderWidth, borderHeight);
        final int tileSize = size / IMAGE_SIZE;
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                double pixelColor = data[j][i];
                assert pixelColor <= 1 && pixelColor >= 0;
                g.setColor(new Color((float) pixelColor, (float) pixelColor, (float) pixelColor));
                g.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }
    }

}
