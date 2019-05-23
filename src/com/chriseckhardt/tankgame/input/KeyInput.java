package com.chriseckhardt.tankgame.input;

import com.chriseckhardt.tankgame.gameobjects.Tank;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Tank tank;
    private final int up;
    private final int down;
    private final int right;
    private final int left;
    private final int shoot;
    private final int mine;

    public KeyInput(Tank tank, int up, int down, int left, int right, int shoot, int mine) {
        this.tank = tank;
        this.up = up;
        this.down = down;
        this.right = right;
        this.left = left;
        this.shoot = shoot;
        this.mine = mine;
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int keyPressed = ke.getKeyCode();
        if (keyPressed == up) {
            this.tank.toggleUpPressed();
        }
        if (keyPressed == down) {
            this.tank.toggleDownPressed();
        }
        if (keyPressed == left) {
            this.tank.toggleLeftPressed();
        }
        if (keyPressed == right) {
            this.tank.toggleRightPressed();
        }
        if(keyPressed == shoot) {
            this.tank.toggleShootPressed();
        }
        if(keyPressed == mine) {
            this.tank.layMine();
        }


    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int keyReleased = ke.getKeyCode();
        if (keyReleased  == up) {
            this.tank.unToggleUpPressed();
        }
        if (keyReleased == down) {
            this.tank.unToggleDownPressed();
        }
        if (keyReleased  == left) {
            this.tank.unToggleLeftPressed();
        }
        if (keyReleased  == right) {
            this.tank.unToggleRightPressed();
        }
        if(keyReleased == shoot) {
            this.tank.unToggleShootPressed();
        }
        if(keyReleased == mine) {
            //the tank only lays one, then resets to false
        }

    }
}
