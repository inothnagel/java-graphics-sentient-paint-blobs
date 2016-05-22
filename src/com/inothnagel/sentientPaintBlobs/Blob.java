package com.inothnagel.sentientPaintBlobs;

import java.awt.*;

class Blob {
    public static final int MAX_SIZE = 20;
    private int x = 40;
    private int y = 40;
    private int xVelocity = 0;
    private int yVelocity = 0;
    private int maxX;
    private int maxY;
    private int size;
    private int yMidpoint;
    private int xMidpoint;
    private Graphics graphics;
    private Canvas canvas;
    private double gravityWeight;

    Blob(Canvas canvas, int x, int y, int maxX, int maxY) {
        this.x = x;
        this.y = y;
        this.maxX = maxX;
        this.maxY = maxY;
        this.yMidpoint = maxY / 2;
        this.xMidpoint = maxX / 2;
        this.canvas = canvas;
        this.graphics = canvas.getBufferStrategy().getDrawGraphics();
        this.gravityWeight = Math.random();
        this.size = (int) (Math.random() * MAX_SIZE);
    }

    void move() {
        updateMidPoint();
        graphics.setColor(getColor());

        yVelocity += yGravity() * gravityWeight;
        xVelocity += xGravity() * gravityWeight;

        yVelocity -= yVelocity / 10;
        xVelocity -= xVelocity / 10;

        x = x + xVelocity;
        y = y + yVelocity;

        paint();
    }

    private void updateMidPoint() {
        if (canvas.getMousePosition() != null) {
            yMidpoint = (int) canvas.getMousePosition().getY();
            xMidpoint = (int) canvas.getMousePosition().getX();
        }
    }

    private int maxOffset() {
        return maxY / 2;
    }

    private int offset() {
        return yMidpoint - y;
    }

    private int yGravity() {
        return (yMidpoint - y) / 2;
    }

    private int xGravity() {
        return (xMidpoint - x) / 2;
    }

    private void paint() {
        graphics.fillOval(x, y, size, size);
    }

    private int getYOffset() {
        int yOffset = Math.abs(offset()) * 250 / Math.abs(maxOffset());
        yOffset = constrain(yOffset, 0, 250);
        return yOffset;
    }

    private int constrain(int val, int min, int max) {
        val = Math.min(max, val);
        val = Math.max(min, val);
        return val;
    }

    private int getXOffset() {
        int xOffset = x * 250 / maxX;
        xOffset = constrain(xOffset, 0, 250);
        return xOffset;
    }

    private Color getColor() {
        int yOffset = getYOffset();
        int xOffset = getXOffset();

        int red = Math.min(250, xOffset + yOffset);
        int green = Math.min(250, yOffset);
        int blue = Math.min(250, 250 - xOffset + yOffset);

        return new Color(red, green, blue);
    }
}
