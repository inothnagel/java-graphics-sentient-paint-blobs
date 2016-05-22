package com.inothnagel.sentientPaintBlobs;

import java.awt.*;

class Blob {
    public static final double MAX_SIZE = 200;
    public static final int GRAVITY_RATIO = 5;
    private static final boolean PRINT_COORDINATES = true;
    private double x = 40;
    private double y = 40;
    private double xVelocity = 0;
    private double yVelocity = 0;
    private double maxPixelWidth;
    private double maxPixelHeight;
    private double size;
    private double yMidpoint;
    private double xMidpoint;
    private Graphics graphics;
    private Canvas canvas;
    private double gravityWeight;
    private double rangeX = 10000;
    private double rangeY = 10000;

    Blob(Canvas canvas, double x, double y, int maxPixelWidth, int maxPixelHeight) {
        this.x = x;
        this.y = y;
        int maxPixels = Math.max(maxPixelWidth, maxPixelHeight);
        this.maxPixelWidth = maxPixels;
        this.maxPixelHeight = maxPixels;
        this.yMidpoint = maxPixelHeight / 2;
        this.xMidpoint = maxPixelWidth / 2;
        this.canvas = canvas;
        this.graphics = canvas.getBufferStrategy().getDrawGraphics();
        this.gravityWeight = Math.random();
        this.size = Math.random() * MAX_SIZE;
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
            double mouseX = canvas.getMousePosition().getX();
            double mouseY = canvas.getMousePosition().getY();

            xMidpoint = rangeX(mouseX);
            yMidpoint = rangeY(mouseY);

            printCoordinates(mouseX, mouseY);
        }
    }

    private void printCoordinates(double mouseX, double mouseY) {
        if (PRINT_COORDINATES == false) { return; }
        graphics.setColor(new Color(200,200,200));
        graphics.drawString("Mouse: " + mouseX + " " + mouseY,20,20);
        graphics.drawString("Field: " + xMidpoint + " " + yMidpoint,20,40);
    }

    private double rangeX(double mouseX) {
        return mouseX * pixelWidth();
    }

    private double rangeY(double mouseY) {
        return mouseY * pixelHeight();
    }

    private double maxOffset() {
        return rangeY / 2;
    }

    private double getYOffset() {
        return yMidpoint - y;
    }

    private double yGravity() {
        return (yMidpoint - y) / GRAVITY_RATIO;
    }

    private double xGravity() {
        return (xMidpoint - x) / GRAVITY_RATIO;
    }

    private void paint() {
        graphics.fillOval(displayX(x), displayY(y), pixelWidth(size), pixelHeight(size));
    }

    private double pixelHeight() {
        return rangeY / maxPixelHeight;
    }

    private double pixelWidth() {
        return rangeX / maxPixelWidth;
    }

    private int pixelWidth(double rangeWidth) {
        return (int) (rangeWidth / pixelWidth());
    }

    private int pixelHeight(double rangeHeight) {
        return (int) (rangeHeight / pixelHeight());
    }

    private int displayY(double y) {
        return (int) Math.round(y / pixelHeight());
//        return (int) Math.round(y);
    }

    private int displayX(double x) {
        return (int) Math.round(x / pixelWidth());
//        return (int) Math.round(x);
    }

    private double getYOffsetPercentage() {
        return Math.min(1, Math.abs(getYOffset() / maxOffset()));
    }

    private double constrain(double val, double min, double max) {
        val = Math.min(max, val);
        val = Math.max(min, val);
        return val;
    }

    private double getXOffsetPercentage() {
        return Math.min(1, Math.abs(x / rangeX));
    }

    private Color getColor() {
        double xOffset = getXOffsetPercentage();
        double yOffset = getYOffsetPercentage();

        int red = (int) (xOffset * 250);
        int green = (int) (yOffset * 250);
        int blue = (int) (250 - (xOffset * 250));

        return new Color(red, green, blue);
    }
}
