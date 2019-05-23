package com.chriseckhardt.tankgame.gameobjects.blocks;

import com.chriseckhardt.tankgame.gameobjects.TYPE;

import java.awt.*;
import java.awt.image.BufferedImage;

public class IndestructibleBlock extends Block {

    private TYPE type = TYPE.INDESTRUCTIBLE_BLOCK;
    private BufferedImage image;
    private Rectangle bounds =  new Rectangle( (int) this.getX(), (int) this.getY(), 32, 32 );

    public IndestructibleBlock(float x, float y, BufferedImage image) {
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
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public TYPE getType() {
        return type;
    }

    @Override
    public void damage(int damage) {

    }

}
