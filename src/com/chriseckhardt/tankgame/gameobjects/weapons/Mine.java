package com.chriseckhardt.tankgame.gameobjects.weapons;

import com.chriseckhardt.tankgame.gameobjects.TYPE;
import com.chriseckhardt.tankgame.graphics.Animation;
import com.chriseckhardt.tankgame.state.GameHandler;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Mine extends Weapon {

    private TYPE type;

    private GameHandler gameHandler;
    private Animation explosion = null;
    private boolean exploding = false;
    private int animationIndex = 1;
    private int damage = 40;
    private Rectangle bounds = new Rectangle((int) this.getX()-12,
            (int) this.getY()-12,
            75, 75);

    public Mine(float x, float y, TYPE type, GameHandler gameHandler, BufferedImage[] images){
        super(x, y, gameHandler);
        this.type = type;
        this.gameHandler = gameHandler;
        explosion = new Animation(images);
    }


    @Override
    public void update() {
        if(exploding) {
            damage = 0;
            animationIndex++;
            explosion.setNextFrame();
            if(animationIndex == 40) {
                gameHandler.removeWeapon(this);
            }
        }
    }

    @Override
    public void drawImage(Graphics g) {
        g.drawImage(explosion.getFrame(),
                (int) this.getX(),
                (int) this.getY(),
                null);

        ///g.setColor(Color.RED);
        //g.drawRect((int) this.getX()-12,(int) this.getY()-12,75,75 );
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public TYPE getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public void explode() {
        exploding = true;
    }

}
