package com.group6;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class CustomSliderUI extends BasicSliderUI {

    private Color thumbColor;
    private Color trackColor;

    public CustomSliderUI(JSlider slider, Color thumbColor, Color trackColor) {
        super(slider);
        this.thumbColor = thumbColor;
        this.trackColor = trackColor;
    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(20, 20); // Set the size of the thumb
    }

    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(thumbColor);
        g2d.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);

        g2d.dispose();
    }

    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(trackColor);
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            int trackY = trackRect.y + trackRect.height / 2 - 2;
            g2d.fillRect(trackRect.x, trackY, trackRect.width, 4);
        } else { // Vertical slider
            int trackX = trackRect.x + trackRect.width / 2 - 2;
            g2d.fillRect(trackX, trackRect.y, 4, trackRect.height);
        }

        g2d.dispose();
    }
}
