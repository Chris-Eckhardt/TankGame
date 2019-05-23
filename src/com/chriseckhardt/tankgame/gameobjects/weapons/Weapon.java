package com.chriseckhardt.tankgame.gameobjects.weapons;

import com.chriseckhardt.tankgame.gameobjects.GameObject;
import com.chriseckhardt.tankgame.state.GameHandler;

public abstract class Weapon extends GameObject {

    private GameHandler gameHandler;

    Weapon(float x, float y, GameHandler gameHandler) {
        super(x, y);
        this.gameHandler = gameHandler;
    }

    GameHandler getGsm() { return gameHandler; }
    public abstract int getDamage();
    public abstract void explode();
}
