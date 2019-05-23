package com.chriseckhardt.tankgame.util;

import com.chriseckhardt.tankgame.gameobjects.Tank;

public class Camera {
    private float x, y, viewWidth, viewHeight, worldWidth, worldHeight;

    public Camera(float x, float y, int viewWidth, int viewHeight, int worldWidth, int worldHeight) {
        this.x = x;
        this.y = y;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void update(Tank tank) {
        x = (tank.getX() - viewWidth/2f);
        y = (tank.getY() - viewHeight/2f);

        if(x <= 0) x = 0;
        if(x >= worldWidth-viewWidth) x = worldWidth-viewWidth;
        if(y <= 0) y = 0;
        if(y >= worldHeight-viewHeight) y = worldHeight-viewHeight;
    }

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
