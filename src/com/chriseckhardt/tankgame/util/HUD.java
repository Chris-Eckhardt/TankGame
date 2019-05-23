package com.chriseckhardt.tankgame.util;

import com.chriseckhardt.tankgame.Game;
import com.chriseckhardt.tankgame.gameobjects.Tank;
import com.chriseckhardt.tankgame.graphics.ImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

import static com.chriseckhardt.tankgame.Game.*;

public class HUD{

    private final static int x1 = 20, y1 = 650, x2 = 840, y2 = 650;
    private Tank t1, t2;
    private BufferedImage greenLeft,
            greenMiddle, greenRight,
            containerLeft, shadowLeft,
            shadowMiddle, shadowRight;
    private int health1, health2, lives1, lives2, ammo1, ammo2, mine1, mine2;
    private Game game;

    public HUD(Tank t1, Tank t2, Game game) {
        this.t1 = t1;
        this.t2 = t2;
        this.game = game;
        init();
    }

    private void init() {
        ImageLoader loader = new ImageLoader();
        containerLeft = loader.loadImage("/gui/boxBlue.png");
        greenLeft = loader.loadImage("/gui/barHorizontal_green_left.png");
        greenMiddle = loader.loadImage("/gui/barHorizontal_green_mid.png");
        greenRight = loader.loadImage("/gui/barHorizontal_green_right.png");
        shadowLeft = loader.loadImage("/gui/barHorizontal_shadow_left.png");
        shadowMiddle = loader.loadImage("/gui/barHorizontal_shadow_mid.png");
        shadowRight = loader.loadImage("/gui/barHorizontal_shadow_right.png");

    }


    public void update() {
        if(t1 != null || t2 != null ){
            health1 = t1.getHealth();
            health2 = t2.getHealth();
            lives1 = t1.getLives();
            lives2 = t2.getLives();
            ammo1 = t1.getAmmoCount();
            ammo2 = t2.getAmmoCount();
            mine1 = t1.getMineCount();
            mine2 = t2.getMineCount();
        }
    }


    public void drawImage(Graphics g) {
        ///// PLAYER 1 ////

        ///// CONTAINER ////
        g.drawImage(containerLeft, x1, y1, null);
        g.setColor(Color.white);
        g.drawString("Player 1    Lives: " + lives1, x1+20, y1+20);

        ///// SHADOW /////
        g.drawImage(shadowLeft, x1 + 10, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 15, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 30, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 45, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 60, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 75, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 90, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 105, y1 + 30, null);
        g.drawImage(shadowMiddle, x1 + 120, y1 + 30, null);
        g.drawImage(shadowRight, x1 + 135, y1 + 30, null);

        ///// GREEN BAR PLAYER 1 ////
        if(health1 > 0)
            g.drawImage(greenLeft, x1 + 10, y1 + 30, null);
        if(health1 >= 10)
            g.drawImage(greenMiddle, x1 + 15, y1 + 30, null);
        if(health1 >= 20)
            g.drawImage(greenMiddle, x1 + 30, y1 + 30, null);
        if(health1 >= 30)
            g.drawImage(greenMiddle, x1 + 45, y1 + 30, null);
        if(health1 >= 40)
            g.drawImage(greenMiddle, x1 + 60, y1 + 30, null);
        if(health1 >= 50)
            g.drawImage(greenMiddle, x1 + 75, y1 + 30, null);
        if(health1 >= 60)
            g.drawImage(greenMiddle, x1 + 90, y1 + 30, null);
        if(health1 >= 70)
            g.drawImage(greenMiddle, x1 + 105, y1 + 30, null);
        if(health1 >= 80)
            g.drawImage(greenMiddle, x1 + 120, y1 + 30, null);
        if(health1 >= 90)
            g.drawImage(greenRight, x1 + 135, y1 + 30, null);

        g.setColor(Color.BLACK);
        g.drawString("AMMO: " + ammo1, x1 + 10, y1 + 70);
        g.drawString("MINES: " + mine1, x1 + 10, y1 + 85);

        ///// PLAYER 2 ////

        ///// CONTAINER ////
        g.drawImage(containerLeft, x2, y2, null);
        g.setColor(Color.white);
        g.drawString("Player 2    Lives: " + lives2, x2+20, y2+20);

        ///// SHADOW /////
        g.drawImage(shadowLeft, x2 + 25, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 30, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 45, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 60, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 75, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 90, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 105, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 120, y2 + 30, null);
        g.drawImage(shadowMiddle, x2 + 135, y2 + 30, null);
        g.drawImage(shadowRight, x2 + 150, y2 + 30, null);

        ///// GREEN BAR PLAYER 2 ////
        if(health2 >= 90)
            g.drawImage(greenLeft, x2 + 25, y2 + 30, null);
        if(health2 >= 80)
            g.drawImage(greenMiddle, x2 + 30, y2 + 30, null);
        if(health2 >= 70)
            g.drawImage(greenMiddle, x2 + 45, y2 + 30, null);
        if(health2 >= 60)
            g.drawImage(greenMiddle, x2 + 60, y2 + 30, null);
        if(health2 >= 50)
            g.drawImage(greenMiddle, x2 + 75, y2 + 30, null);
        if(health2 >= 40)
            g.drawImage(greenMiddle, x2 + 90, y2 + 30, null);
        if(health2 >= 30)
            g.drawImage(greenMiddle, x2 + 105, y2 + 30, null);
        if(health2 >= 20)
            g.drawImage(greenMiddle, x2 + 120, y2 + 30, null);
        if(health2 >= 10)
            g.drawImage(greenMiddle, x2 + 135, y2 + 30, null);
        if(health2 > 0)
            g.drawImage(greenRight, x2 + 150, y2 + 30, null);

        ////// AMMO //////
        g.setColor(Color.BLACK);
        g.drawString("AMMO: " + ammo2, x2 + 25, y2 + 70);
        g.drawString("MINES: " + mine2, x2 + 25, y2 + 85);

        // MINI MAP
        g.drawImage(game.getWorld(), FRAME_WIDTH/3, FRAME_HEIGHT-200, 300,200,  null);
    }

}
