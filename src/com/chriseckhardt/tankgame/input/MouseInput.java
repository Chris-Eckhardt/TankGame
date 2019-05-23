package com.chriseckhardt.tankgame.input;

import com.chriseckhardt.tankgame.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    private int x = 0, y = 0;

    public MouseInput() {}

    @Override
    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int getX() {
        return  x;
    }

    public int getY() {
        return y;
    }
}
