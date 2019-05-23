package com.chriseckhardt.tankgame.gameobjects.blocks;

import com.chriseckhardt.tankgame.gameobjects.TYPE;
import com.chriseckhardt.tankgame.state.GameHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DestructibleBlock extends Block {

    private TYPE type = TYPE.DESTRUCTIBLE_BLOCK;

    private int health = 50;
    private GameHandler gameHandler;
    private BufferedImage[] images;
    private BufferedImage currentImage;
    private Rectangle bounds =  new Rectangle( (int) this.getX(), (int) this.getY(), 32, 32 );

    public DestructibleBlock(float x, float y, GameHandler gameHandler, BufferedImage[] images) {
        super(x, y);
        this.gameHandler = gameHandler;
        this.images = images;

        currentImage = images[0];
    }

    @Override
    public void update() {
        if (health > 20) {
            currentImage = images[0];
        } else {
            currentImage = images[1];
        }
    }

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(currentImage, (int) this.getX(), (int) this.getY(), null);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public TYPE getType() {
        return type;
    }

    public void damage(int damage) {
        health -= damage;
        if(health <= 0) {
            gameHandler.removeBlock(this);
        }
    }
}
