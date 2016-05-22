package com.inothnagel.sentientPaintBlobs;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class SentientPaintBlobs extends JFrame{
    private final static int MAX_BLOBS = 400;
    private List<Blob> blobs = new ArrayList<>();
    private Canvas canvas= new Canvas();

    private SentientPaintBlobs(){
        super();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        canvas.setSize(getSize());
        add(canvas);
        setVisible(true);
        canvas.createBufferStrategy(2);
    }

    public static void main(String arg[]) throws InterruptedException {
        SentientPaintBlobs sentientPaintBlobs = new SentientPaintBlobs();
        sentientPaintBlobs.mainLoop();
    }

    private void moveBlobs() {
        blobs.forEach(Blob::move);
    }

    private void mainLoop() throws InterruptedException {
        while (true) {
            addBlob();
            Graphics graphics = canvas.getBufferStrategy().getDrawGraphics();
            clearBackground(graphics);
            moveBlobs();
            canvas.getBufferStrategy().show();
            sleep(40);
        }
    }

    private void clearBackground(Graphics graphics) {
        graphics.setColor(new Color(0, 0, 0));
        graphics.fillRect(0,0,getSize().width, getSize().height);
    }

    private void addBlob() {
        if (Math.random() < 0.9) {return;}
        if (blobs.size() >= MAX_BLOBS) {return;}
        if (canvas.getMousePosition() == null) {return; }

        int x = (int) canvas.getMousePosition().getX();
        int y = (int) canvas.getMousePosition().getY();
        blobs.add(new Blob(canvas, x, y, getSize().width, getSize().height));
    }
}
