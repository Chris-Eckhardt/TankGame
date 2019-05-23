package com.chriseckhardt.tankgame.gameobjects.powerups;

import com.chriseckhardt.tankgame.gameobjects.TYPE;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MineCrate extends PowerUp {

    private TYPE type = TYPE.MINE_CRATE;
    private BufferedImage image;
    private Rectangle bounds =  new Rectangle( (int) this.getX(), (int) this.getY(), 32, 32 );


    public MineCrate(float x, float y, BufferedImage image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void update() {
        // N/A
    }

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(image, (int) this.getX(), (int) this.getY(), null);
    }

    @Override
    public TYPE getType() {
        return type;
    }

    @Override
    public int getQuantity() {
        return 5;
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
