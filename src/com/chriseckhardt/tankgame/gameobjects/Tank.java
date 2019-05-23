package com.chriseckhardt.tankgame.gameobjects;

import com.chriseckhardt.tankgame.gameobjects.powerups.PowerUp;
import com.chriseckhardt.tankgame.gameobjects.weapons.Mine;
import com.chriseckhardt.tankgame.gameobjects.weapons.Projectile;
import com.chriseckhardt.tankgame.state.GameHandler;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Tank extends GameObject {

    private float previous_x, previous_y;
    private double velX, velY, angle;
    private int frameDelayIndex = 0;
    private static final int MAX_HEALTH = 100;
    private int health = 100, ammoCount = 20, mineCount = 0, lives = 3;
    private int firstRespawnX, firstRespawnY, secondRespawnX, secondRespawnY;

    private final int R = 2;
    private final int ROTATIONSPEED = 4;
    private TYPE tankType, projectileType, mineType;
    private Rectangle bounds =  new Rectangle( (int) this.getX()+16, (int) this.getY()+16, 32, 32 );


    private GameHandler gameHandler;
    private BufferedImage tankImage;
    private BufferedImage[] mineImages = new BufferedImage[5], projectileImages = new BufferedImage[5];
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean shootPressed;
    private boolean layMine;
    private boolean dead = false;



    public Tank(float x, float y, double velX, double velY, double angle, TYPE type, GameHandler gameHandler, BufferedImage[] images) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
        this.angle = angle;
        this.tankType = type;
        this.gameHandler = gameHandler;

        loadImages(images);

        if(type == TYPE.TANK_2) {
            projectileType = TYPE.PROJECTILE_2;
            mineType = TYPE.MINE_2;
            firstRespawnX = 1376;
            firstRespawnY = 80;
            secondRespawnX = 1376;
            secondRespawnY = 700;
        } else {
            projectileType = TYPE.PROJECTILE_1;
            mineType = TYPE.MINE_1;
            firstRespawnX = 100;
            firstRespawnY = 80;
            secondRespawnX = 100;
            secondRespawnY = 700;
        }
    }

    @Override
    public void update() {

        if (this.health <= 0) {              //CHECK FOR DEATH
            dead = true;
        } else {

            previous_x = getX();
            previous_y = getY();

            // check for input
            if (this.UpPressed) {
                this.moveForwards();
            }
            if (this.DownPressed) {
                this.moveBackwards();
            }
            if (this.LeftPressed) {
                this.rotateLeft();
            }
            if (this.RightPressed) {
                this.rotateRight();
            }
            if (this.shootPressed) {
                //check for frame delay
                int fireDelay = 30;
                if (frameDelayIndex > fireDelay) {
                    // check for ammo count
                    if (ammoCount > 0) {
                        gameHandler.addWeapon(new Projectile((int) this.getX() + 16, (int) this.getY() + 16, projectileType, gameHandler, projectileImages, 10.0f, angle));
                        ammoCount--;
                        frameDelayIndex = 0;
                    }
                }
            }
            if (this.layMine) {
                if (mineCount > 0) {
                    gameHandler.addWeapon(new Mine((int) this.getX() + 16, (int) this.getY() + 16, mineType, gameHandler, mineImages));
                    mineCount--;
                }
                layMine = false;
            }
            frameDelayIndex++; // increment frame index

            bounds.setBounds((int) this.getX()+4, (int) this.getY()+4, 32, 32);

        }
    }

    @Override
    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.getX(), this.getY());
        rotation.rotate(Math.toRadians(angle), this.tankImage.getWidth() / 2.0, this.tankImage.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.tankImage, rotation, null);
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    public void toggleShootPressed() {
        this.shootPressed = true;
    }

    public void unToggleShootPressed() {
        this.shootPressed = false;
    }

    public void layMine() {
        this.layMine = true;
    }

    private void moveBackwards() {
        velX = R * Math.cos(Math.toRadians(angle));
        velY = R * Math.sin(Math.toRadians(angle));
        this.setX( getX() - (float) velX);
        this.setY( getY() - (float) velY);
    }

    private void moveForwards() {
        velX = R * Math.cos(Math.toRadians(angle));
        velY = R * Math.sin(Math.toRadians(angle));
        this.setX( getX() + (float) velX);
        this.setY( getY() + (float) velY);
    }

    @Override
    public String toString() {
        return "x=" + getX() + ", y=" + getY() + ", angle=" + angle;
    }

    public TYPE getType() {
        return tankType;
    }

    public void damage(int d) {
        health -= d;
    }

    public void wallCollisionVert() {
        this.setY(previous_y);
    }

    public void wallCollisionHoriz() {
        this.setX(previous_x);
    }

    public void powerUp(PowerUp powerUp) {
        if(powerUp.getType() == TYPE.HEALTH_CRATE) {
            health += powerUp.getQuantity();
            if(this.health >= MAX_HEALTH) {
                this.health = MAX_HEALTH;
            }
        }
        if(powerUp.getType() == TYPE.AMMO_CRATE) {
            ammoCount += powerUp.getQuantity();
        }
        if(powerUp.getType() == TYPE.MINE_CRATE) {
            mineCount += powerUp.getQuantity();
        }
    }

    public int getHealth() {
        return  health;
    }
    public int getAmmoCount() { return ammoCount; }
    public int getMineCount() { return mineCount; }
    public int getLives() { return lives; }

    private void loadImages(BufferedImage[] images) {

        tankImage = images[0];

        projectileImages[0] = images[1];
        projectileImages[1] = images[3];
        projectileImages[2] = images[4];
        projectileImages[3] = images[5];
        projectileImages[4] = images[6];

        mineImages[0] = images[2];
        mineImages[1] = images[3];
        mineImages[2] = images[4];
        mineImages[3] = images[5];
        mineImages[4] = images[6];


    }

    public boolean isDead() {
        return dead;
    }

    public void loseLife() {
        lives--;
        this.respawn();
    }

    private void respawn() {
        if(lives > 0) {
            dead = false;
            health = 100;
            ammoCount = 20;
            if(lives == 2) {
                this.setX(firstRespawnX);
                this.setY(firstRespawnY);
            }
            if(lives == 1) {
                this.setX(secondRespawnX);
                this.setY(secondRespawnY);
            }
        } else {
            gameHandler.gameOver(this);
        }
    }
}
