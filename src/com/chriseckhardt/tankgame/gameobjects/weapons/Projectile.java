package com.chriseckhardt.tankgame.gameobjects.weapons;

import com.chriseckhardt.tankgame.gameobjects.TYPE;
import com.chriseckhardt.tankgame.graphics.Animation;
import com.chriseckhardt.tankgame.state.GameHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Projectile extends Weapon {

    private TYPE type;

    private int damage = 35, animationIndex = 0;
    private boolean exploding = false;
    private double angle;
    private double projectileSpeed;
    private GameHandler gameHandler;
    private Animation explosion;
    private Rectangle bounds =  new Rectangle( (int) this.getX(),(int) this.getY(), 10, 10 );

    public Projectile(float x, float y, TYPE type, GameHandler gameHandler, BufferedImage[] images, double projectileSpeed, double angle) {
        super(x, y, gameHandler);
        this.type = type;
        this.projectileSpeed = projectileSpeed;
        this.angle = angle;
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
        } else {
            int r = 2;
            double velX = (r * Math.cos(Math.toRadians(angle)) * projectileSpeed);
            double velY = (r * Math.sin(Math.toRadians(angle)) * projectileSpeed);
            this.setX(this.getX() + (float) velX);
            this.setY(this.getY() + (float) velY);

            bounds.setBounds((int) this.getX(), (int) this.getY(), 10, 10);
        }

    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.getX(), this.getY());
        rotation.rotate(Math.toRadians(angle), this.explosion.getFrame().getWidth() / 2.0, this.explosion.getFrame().getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.explosion.getFrame(), rotation, null);
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

    @Override
    public void explode() {
        exploding = true;
    }
}
