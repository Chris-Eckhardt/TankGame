package com.chriseckhardt.tankgame.graphics;

import java.awt.image.BufferedImage;

public class Animation {

    private BufferedImage[] images;
    private BufferedImage currentImage;
    private int tick = 0;
    private int index = 0;

    public Animation(BufferedImage[] images) {
        this.images = images;
        currentImage = images[0];
    }

    public BufferedImage getFrame() {
        return currentImage;
    }

    public void setNextFrame() {
        tick++;
        int delay = 10;
        if (tick >= delay) {
            tick = 0;
            index++;
            if (index == images.length) {
                index = 0;
            }
            currentImage = images[index];
        }
    }
}
