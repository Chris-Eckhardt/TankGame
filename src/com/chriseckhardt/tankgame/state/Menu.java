package com.chriseckhardt.tankgame.state;

import com.chriseckhardt.tankgame.Game;
import com.chriseckhardt.tankgame.input.MouseInput;

import java.awt.*;

import static com.chriseckhardt.tankgame.Game.FRAME_HEIGHT;
import static com.chriseckhardt.tankgame.Game.FRAME_WIDTH;

public class Menu {

    private Game game;
    private MouseInput mouse;
    private Rectangle map1Button = new Rectangle(FRAME_WIDTH/3-50, 250,120, 50);
    private Rectangle map2Button = new Rectangle(FRAME_WIDTH/3-50,350, 120, 50);
    private Rectangle map3Button = new Rectangle(FRAME_WIDTH/3-50, 450, 120, 50);
    private Rectangle map4Button = new Rectangle(FRAME_WIDTH/3-50, 550, 120, 50);

    public Menu(Game game, MouseInput mouse) {
        this.game = game;
        this.mouse = mouse;
    }

    public void update() {
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();

        if(map1Button.contains(mouseX, mouseY)) {
            game.startRound(1);
        }
        if(map2Button.contains(mouseX, mouseY)) {
            game.startRound(2);
        }
        if(map3Button.contains(mouseX, mouseY)) {
            game.startRound(3);
        }
        if(map4Button.contains(mouseX, mouseY)) {
            game.startRound(4);
        }
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font font1 = new Font("arial", Font.BOLD, 75);
        Font font2 = new Font("arial", Font.BOLD, 30);
        Font font3 = new Font("arial", Font.BOLD, 12);

        g.setFont(font1);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        g.setColor(Color.white);
        g.drawString("TANK WARS", FRAME_WIDTH/2-220, 100);

        g.setFont(font2);
        g.drawString("MAP 1", map1Button.x + 10, map1Button.y + 35);
        g.drawString("MAP 2", map2Button.x + 10, map2Button.y + 35);
        g.drawString("MAP 3", map3Button.x + 10, map3Button.y + 35);
        g.drawString("MAP 4", map4Button.x + 10, map4Button.y + 35);

        g2d.draw(map1Button);
        g2d.draw(map2Button);
        g2d.draw(map3Button);
        g2d.draw(map4Button);

        g.setFont(font3);
        g.drawString("CONTROLS   :       PLAYER 1           PLAYER 2", (FRAME_WIDTH/2), 250);
        g.drawString("MOVEMENT   :       w,a,s,d              arrow-keys", (FRAME_WIDTH/2), 270);
        g.drawString("SHOOT           :       space                enter", (FRAME_WIDTH/2), 290);
        g.drawString("MINES            :         e                        /", (FRAME_WIDTH/2), 310);
    }
}
