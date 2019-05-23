package com.chriseckhardt.tankgame.gameobjects.blocks;

import com.chriseckhardt.tankgame.gameobjects.GameObject;

public abstract class Block extends GameObject {

    Block(float x, float y) {
        super(x, y);
    }

    public abstract void damage(int damage);
}
