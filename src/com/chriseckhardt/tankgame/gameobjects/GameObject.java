package com.chriseckhardt.tankgame.gameobjects;

import java.awt.*;


public abstract class GameObject {

    // PRIVATE DATA
    private float x, y;

    // CONSTRUCTOR
    public GameObject(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // ABSTRACT METHODS
    public abstract void update();
    public abstract void drawImage(Graphics g);
    public abstract Rectangle getBounds();
    public abstract TYPE getType();

    // GETTERS AND SETTERS
    public float getX() {
        return x;
    }
    public void setX(float x) {
        this.x = x;
    }
    public float getY() {
        return y;
    }
    public void setY(float y) {
        this.y = y;
    }

}