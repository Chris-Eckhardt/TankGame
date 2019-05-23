package com.chriseckhardt.tankgame.gameobjects.powerups;

import com.chriseckhardt.tankgame.gameobjects.GameObject;

public abstract class PowerUp extends GameObject {

    PowerUp(float x, float y) {
        super(x, y);
    }

    public abstract int getQuantity();

}
